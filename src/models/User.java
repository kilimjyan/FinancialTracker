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


    public User(User other) {
        this.username = other.username;
        this.email = other.email;
        this.paymentMethods = new ArrayList<>();
        this.transactions = new ArrayList<>();


        for (PaymentType paymentType : other.paymentMethods) {
            if (paymentType instanceof CreditCard) {
                this.paymentMethods.add(new CreditCard((CreditCard)paymentType));
            } else if (paymentType instanceof BankTransfer) {
                this.paymentMethods.add(new BankTransfer((BankTransfer)paymentType));
            } else if (paymentType instanceof Cash) {
                this.paymentMethods.add(new Cash());
            }
        }

        for (Transaction transaction : other.transactions) {
            this.transactions.add(new Transaction(transaction));
        }
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
        paymentMethods.clear();
        transactions.clear();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null)
            return false;
        else if (getClass() != otherObject.getClass())
            return false;
        else {
            User otherUser = (User)otherObject;
            return (username.equals(otherUser.username)
                    && email.equals(otherUser.email));
        }
    }
}
