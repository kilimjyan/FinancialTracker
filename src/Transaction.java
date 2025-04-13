import java.time.LocalDateTime;

public class Transaction {
    public int getAmount() {
        return amount;
    }

    public String getTransactionID() {
        return transactionID;
    }

    private String transactionID;
    private PaymentType paymentType;
    private int amount;
    private LocalDateTime date;
    protected enum TransactionStatus {PENDING, SUCCESSFUL, FAILED};
    private TransactionStatus status;

    public Transaction(String transactionID, PaymentType paymentType, int amount) {
        this.transactionID = transactionID;
        this.paymentType = paymentType;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }

    public void executeTransaction(boolean isExpense) {
        try {
            if (isExpense) {
                ((PaymentFunctionality)paymentType).deduct(amount);
            } else {
                ((PaymentFunctionality)paymentType).add(amount);
            }
            status = TransactionStatus.SUCCESSFUL;
        } catch (Exception e) {
            status = TransactionStatus.FAILED;
        }
    }

    public void printTransactionDetails() {
        System.out.println("Transaction ID: " + transactionID);
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + date);
        System.out.println("Status: " + status);
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }
}