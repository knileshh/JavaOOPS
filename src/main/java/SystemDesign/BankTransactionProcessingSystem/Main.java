package SystemDesign.BankTransactionProcessingSystem;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
//        System.out.println("Bank Transaction Processing System online!");
        logger.info("Bank Transaction Processing System online!");

    }

}

abstract class Bank {
    private String bankName;
    private String bankAddress;
    private int totalBranches;
    private String acNumber;
    private List<AcNumber> acNumbers;

    public String getBankName() { return bankName; }
    public String getBankAddress() { return bankAddress; }
    public int getTotalBranches() { return totalBranches; }
    public String getAcNumber() { return acNumber; }

    public void setBankName(String bankName) { this.bankName = bankName; }
    public void setBankAddress(String bankAddress) { this.bankAddress = bankAddress; }
    public void setTotalBranches(int totalBranches) { this.totalBranches = totalBranches; }
    public void setAcNumber(String acNumber) { this.acNumber = acNumber; }

    private Bank(String bankName, String bankAddress, int totalBranches) {
        this.bankName = bankName;
        this.bankAddress = bankAddress;
        this.totalBranches = totalBranches;
    }

    abstract void deposit(int amount, String acNumber);
    abstract void withdrawal(int amount, String acNumber);

    private void transfer(int amount, String fromAcNumber, String toAcNumber) {

    }

}
