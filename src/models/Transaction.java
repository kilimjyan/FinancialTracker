package models;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionID;
    private PaymentType paymentType;
    private int amount;
    private LocalDateTime date;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public enum TransactionStatus {PENDING, SUCCESSFUL, FAILED}
    private TransactionStatus status;


    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public int getAmount() {
        return amount;
    }



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
                ( paymentType).deduct(amount);
            } else {
                ( paymentType).add(amount);
            }
            status = TransactionStatus.SUCCESSFUL;
        } catch (Exception e) {
            status = TransactionStatus.FAILED;
        }
    }

    public String toCSV() {
        return transactionID + ", " + paymentType.getClass().getSimpleName() + ", " + amount + ", " + date + ", " + status;
    }
}