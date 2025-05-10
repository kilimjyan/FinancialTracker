package models;

import exceptions.InsufficientFundsException;

public abstract class PaymentType {
    private int income;
    private int expense;
    private int savings;
    private int balance;


    public int getBalance() {
        return balance;
    }

    public int getSavings() {
        return savings;
    }

    public int getExpense() {
        return expense;
    }

    public int getIncome() {
        return income;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }



    public void setSavings(int savings) {
        this.savings = savings;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setIncome(int income) {
        this.income = income;
    }



    public abstract void add(int money);

    public abstract void deduct(int money) throws InsufficientFundsException;

    public abstract void save(int money) throws InsufficientFundsException;

    public abstract String toString();

    protected void printCurrentBalance() {
        System.out.println("Current balance: " + getBalance());
        System.out.println("Income: " + getIncome());
        System.out.println("Expense: " + getExpense());
        System.out.println("Savings: " + getSavings());
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null)
            return false;
        else if (getClass() != otherObject.getClass())
            return false;
        else {
            PaymentType otherPaymentType = (PaymentType)otherObject;
            return (income == otherPaymentType.income
                    && expense == otherPaymentType.expense
                    && savings == otherPaymentType.savings
                    && balance == otherPaymentType.balance);
        }
    }

}
