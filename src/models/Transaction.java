package models;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionID;
    private PaymentType paymentType;
    private int amount;
    private LocalDateTime date;
    private String type;
    private TransactionStatus status;

    public enum TransactionStatus {PENDING, SUCCESSFUL, FAILED}

    public Transaction(String transactionID, PaymentType paymentType, int amount) {
        this.transactionID = transactionID;
        this.paymentType = paymentType;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }


    public Transaction(Transaction other) {
        this.transactionID = other.transactionID;
        this.amount = other.amount;
        this.date = other.date;
        this.type = other.type;
        this.status = other.status;


        if (other.paymentType instanceof CreditCard) {
            this.paymentType = new CreditCard((CreditCard)other.paymentType);
        } else if (other.paymentType instanceof BankTransfer) {
            this.paymentType = new BankTransfer((BankTransfer)other.paymentType);
        } else if (other.paymentType instanceof Cash) {
            this.paymentType = new Cash();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null)
            return false;
        else if (getClass() != otherObject.getClass())
            return false;
        else {
            Transaction otherTransaction = (Transaction)otherObject;
            return (transactionID.equals(otherTransaction.transactionID)
                    && paymentType.equals(otherTransaction.paymentType)
                    && amount == otherTransaction.amount
                    && date.equals(otherTransaction.date)
                    && type.equals(otherTransaction.type)
                    && status == otherTransaction.status);
        }
    }
}