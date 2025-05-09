package models;

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

    public abstract void deduct(int money);

    public abstract void save(int money);

    public abstract String toString();

    public void printCurrentBalance() {
        System.out.println("Current balance: " + getBalance());
        System.out.println("Income: " + getIncome());
        System.out.println("Expense: " + getExpense());
        System.out.println("Savings: " + getSavings());
    }



}

