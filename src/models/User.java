package models;

import java.util.ArrayList;

public class User {
    private String username;
    private String email;
    private ArrayList<PaymentType> paymentMethods;
    private ArrayList<Transaction> transactions;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.paymentMethods = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<PaymentType> getPaymentMethods() {
        return paymentMethods;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    public void addPaymentMethod(PaymentType paymentType) {
        paymentMethods.add(paymentType);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void reset() {
        
    }
}