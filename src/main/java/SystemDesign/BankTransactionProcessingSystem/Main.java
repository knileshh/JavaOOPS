package SystemDesign.BankTransactionProcessingSystem;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Abstract base class for all transactions
abstract class Transaction {
    protected String transactionId;
    protected LocalDateTime timestamp;
    protected String description;

    public Transaction(String transactionId) {
        this.transactionId = transactionId;
        this.timestamp = LocalDateTime.now();
    }

    public abstract boolean execute(Bank bank);

    public String getTransactionId() { return transactionId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getDescription() { return description; }
}

// Interface for transaction processing
interface TransactionProcessor {
    void processTransaction(Transaction transaction);
    void shutdown();
    List<String> getTransactionHistory();
}

// Concrete transaction implementations
class DepositTransaction extends Transaction {
    private String accountNumber;
    private double amount;

    public DepositTransaction(String transactionId, String accountNumber, double amount) {
        super(transactionId);
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = String.format("Deposit $%.2f to account %s", amount, accountNumber);
    }

    @Override
    public boolean execute(Bank bank) {
        return bank.deposit(accountNumber, amount);
    }
}

class WithdrawalTransaction extends Transaction {
    private String accountNumber;
    private double amount;

    public WithdrawalTransaction(String transactionId, String accountNumber, double amount) {
        super(transactionId);
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = String.format("Withdraw $%.2f from account %s", amount, accountNumber);
    }

    @Override
    public boolean execute(Bank bank) {
        return bank.withdraw(accountNumber, amount);
    }
}

class TransferTransaction extends Transaction {
    private String fromAccount;
    private String toAccount;
    private double amount;

    public TransferTransaction(String transactionId, String fromAccount, String toAccount, double amount) {
        super(transactionId);
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.description = String.format("Transfer $%.2f from %s to %s", amount, fromAccount, toAccount);
    }

    @Override
    public boolean execute(Bank bank) {
        return bank.transfer(fromAccount, toAccount, amount);
    }
}

// Account class with thread safety
class Account {
    private final String accountNumber;
    private final String holderName;
    private volatile double balance;
    private final ReentrantLock lock = new ReentrantLock();

    public Account(String accountNumber, String holderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) return false;

        lock.lock();
        try {
            balance += amount;
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) return false;

        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public ReentrantLock getLock() { return lock; }

    @Override
    public String toString() {
        return String.format("Account[%s, %s, $%.2f]", accountNumber, holderName, getBalance());
    }
}

// Main Bank class
class Bank {
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final Logger logger = Logger.getLogger(Bank.class.getName());

    public void createAccount(String accountNumber, String holderName, double initialBalance) {
        Account account = new Account(accountNumber, holderName, initialBalance);
        accounts.put(accountNumber, account);
        logger.info("Account created: " + account);
    }

    public boolean deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            logger.warning("Deposit failed: Account " + accountNumber + " not found");
            return false;
        }

        boolean success = account.deposit(amount);
        if (success) {
            logger.info(String.format("Deposited $%.2f to %s. New balance: $%.2f",
                    amount, accountNumber, account.getBalance()));
        }
        return success;
    }

    public boolean withdraw(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            logger.warning("Withdrawal failed: Account " + accountNumber + " not found");
            return false;
        }

        boolean success = account.withdraw(amount);
        if (success) {
            logger.info(String.format("Withdrew $%.2f from %s. New balance: $%.2f",
                    amount, accountNumber, account.getBalance()));
        } else {
            logger.warning("Withdrawal failed: Insufficient funds in " + accountNumber);
        }
        return success;
    }

    public boolean transfer(String fromAccount, String toAccount, double amount) {
        Account from = accounts.get(fromAccount);
        Account to = accounts.get(toAccount);

        if (from == null || to == null) {
            logger.warning("Transfer failed: One or both accounts not found");
            return false;
        }

        // Prevent deadlock by acquiring locks in consistent order
        Account firstLock = fromAccount.compareTo(toAccount) < 0 ? from : to;
        Account secondLock = fromAccount.compareTo(toAccount) < 0 ? to : from;

        firstLock.getLock().lock();
        try {
            secondLock.getLock().lock();
            try {
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                    logger.info(String.format("Transferred $%.2f from %s to %s",
                            amount, fromAccount, toAccount));
                    return true;
                } else {
                    logger.warning("Transfer failed: Insufficient funds in " + fromAccount);
                    return false;
                }
            } finally {
                secondLock.getLock().unlock();
            }
        } finally {
            firstLock.getLock().unlock();
        }
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Map<String, Account> getAllAccounts() {
        return new HashMap<>(accounts);
    }
}

// Transaction processor implementation
class BankTransactionProcessor implements TransactionProcessor {
    private final Bank bank;
    private final ExecutorService executorService;
    private final List<String> transactionHistory = Collections.synchronizedList(new ArrayList<>());
    private final Logger logger = Logger.getLogger(BankTransactionProcessor.class.getName());

    public BankTransactionProcessor(Bank bank, int threadPoolSize) {
        this.bank = bank;
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    @Override
    public void processTransaction(Transaction transaction) {
        executorService.submit(() -> {
            try {
                boolean success = transaction.execute(bank);
                String result = String.format("[%s] %s %s - %s",
                        transaction.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")),
                        transaction.getTransactionId(),
                        success ? "SUCCESS" : "FAILED",
                        transaction.getDescription());

                transactionHistory.add(result);
                logger.info(result);
            } catch (Exception e) {
                logger.severe("Transaction processing error: " + e.getMessage());
            }
        });
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}

// Reporting service that runs concurrently
class ReportingService {
    private final Bank bank;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Logger logger = Logger.getLogger(ReportingService.class.getName());

    public ReportingService(Bank bank) {
        this.bank = bank;
    }

    public void startPeriodicReporting(int intervalSeconds) {
        scheduler.scheduleAtFixedRate(this::generateAccountSummary,
                0, intervalSeconds, TimeUnit.SECONDS);
    }

    private void generateAccountSummary() {
        logger.info("=== ACCOUNT SUMMARY REPORT ===");
        double totalBalance = 0;

        for (Account account : bank.getAllAccounts().values()) {
            double balance = account.getBalance();
            totalBalance += balance;
            logger.info(String.format("Account %s (%s): $%.2f",
                    account.getAccountNumber(), account.getHolderName(), balance));
        }

        logger.info(String.format("Total bank balance: $%.2f", totalBalance));
        logger.info("================================");
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}

// Main class
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {
        logger.info("Bank Transaction Processing System online!");

        // Create bank and accounts
        Bank bank = new Bank();
        bank.createAccount("ACC001", "Alice Johnson", 1000.0);
        bank.createAccount("ACC002", "Bob Smith", 1500.0);
        bank.createAccount("ACC003", "Charlie Brown", 800.0);

        // Create transaction processor
        BankTransactionProcessor processor = new BankTransactionProcessor(bank, 5);

        // Start reporting service
        ReportingService reportingService = new ReportingService(bank);
        reportingService.startPeriodicReporting(3);

        // Create and process multiple transactions concurrently
        List<Transaction> transactions = Arrays.asList(
                new DepositTransaction("TXN001", "ACC001", 200.0),
                new WithdrawalTransaction("TXN002", "ACC002", 300.0),
                new TransferTransaction("TXN003", "ACC001", "ACC003", 150.0),
                new DepositTransaction("TXN004", "ACC003", 100.0),
                new TransferTransaction("TXN005", "ACC002", "ACC001", 250.0),
                new WithdrawalTransaction("TXN006", "ACC001", 50.0),
                new TransferTransaction("TXN007", "ACC003", "ACC002", 75.0),
                new DepositTransaction("TXN008", "ACC002", 400.0)
        );

        // Process all transactions
        for (Transaction transaction : transactions) {
            processor.processTransaction(transaction);
            Thread.sleep(100); // Small delay to see concurrent processing
        }

        // Wait for processing to complete
        Thread.sleep(2000);

        // Print transaction history
        logger.info("\n=== TRANSACTION HISTORY ===");
        for (String record : processor.getTransactionHistory()) {
            logger.info(record);
        }

        // Final account balances
        Thread.sleep(1000);
        logger.info("\n=== FINAL ACCOUNT BALANCES ===");
        for (Account account : bank.getAllAccounts().values()) {
            logger.info(account.toString());
        }

        // Shutdown services
        processor.shutdown();
        reportingService.shutdown();

        logger.info("System shutdown complete!");
    }
}