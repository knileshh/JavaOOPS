package SystemDesign.BankTransactionProcessingSystem;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;

public class Main {

    static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
//        System.out.println("Bank Transaction Processing System online!");
        logger.info("Bank Transaction Processing System online!");

    }

}

abstract class Transactions {


    void deposit(int balance, String toAcNumber) {

    }
}

class Bank extends Transactions {
    private List<Account> accounts;

    static class Account {
        private String acNumber;
        private int balance;
        private String acHolderName;
        private Bank bank;

        Account(String acNumber, String acHolderName, int balance, Bank bank) {
            this.acNumber = acNumber;
            this.acHolderName = acHolderName;
            this.balance = balance;
            this.bank = bank;

            bank.accounts.add(this);
            Account account = bank.accounts.get(bank.accounts.size() - 1);
            Main.logger.info(account.toString());
        }
    }

    void deposit(int amount, String toAccount) {
        for (Account a : accounts) {
            if (a.acNumber.equals(toAccount)) {
                a.balance += amount;
            } else {
                Main.logger.info("ToAccount Invalid");
            }
        }
    }

    void withdrawal(int amount, String fromAccount) {
        for (Account a : accounts) {
            if (a.acNumber.equals(fromAccount)) {
                a.balance -= amount;
            } else {
                Main.logger.info("From account Invalid");
            }
        }
    }

    void transfer(int amount, String fromAccount, String toAccount) {
        for (Account a : accounts) {
            if (a.acNumber.equals(fromAccount)) {
                if (a.balance < amount) {
                    Main.logger.info("Low balance in fromAc");
                    break;
                }

                if (toAccountPresent()) {
                    a.balance -= amount;
                    Main.logger.info("Transfer complete");
                }
            }
        }
    }
}