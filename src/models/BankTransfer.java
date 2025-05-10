package models;

import exceptions.InsufficientFundsException;

public class BankTransfer extends PaymentType {
    private int tax;
    private int totalTax;

    public BankTransfer() {
        super();
    }

    public int getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(int totalTax) {
        this.totalTax = totalTax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getTax() {
        return tax;
    }


    public BankTransfer(BankTransfer other) {
        super();
        this.tax = other.tax;
        this.totalTax = other.totalTax;
        this.setBalance(other.getBalance());
        this.setIncome(other.getIncome());
        this.setExpense(other.getExpense());
        this.setSavings(other.getSavings());
    }

    @Override
    public void add(int money) {
        setIncome(getIncome() + money - generateTax(money));
        setTax(0);
        setBalance(getBalance() + money - generateTax(money));
    }

    @Override
    public void deduct(int money) throws InsufficientFundsException {
        if (getBalance() < money + generateTax(money)) {
            throw new InsufficientFundsException("Not enough funds to deduct");
        }
        setExpense(getExpense() + money + generateTax(money));
        setTax(0);
        setBalance(getBalance() - money - generateTax(money));
    }

    @Override
    public void save(int money) throws InsufficientFundsException {
        if (getBalance() < money) {
            throw new InsufficientFundsException("Not enough funds to save");
        }
        setSavings(getSavings() + money);
        setBalance(getBalance() - money);
    }

    @Override
    public String toString() {
        return "Bank Transfer";
    }

    protected int generateTax(int money) {
        if (money > 100000) {
            setTax(getTax() + (int) (money * 0.05));
            totalTax += getTax();
        }
        return getTax();
    }
    @Override
    protected void printCurrentBalance() {
        System.out.println("Current balance: " + getBalance());
        System.out.println("Income: " + getIncome());
        System.out.println("Expense: " + getExpense());
        System.out.println("Savings: " + getSavings());
        System.out.println("Tax: " + totalTax);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null)
            return false;
        else if (getClass() != otherObject.getClass())
            return false;
        else {
            BankTransfer otherBankTransfer = (BankTransfer)otherObject;
            return (super.equals(otherObject)
                    && tax == otherBankTransfer.tax
                    && totalTax == otherBankTransfer.totalTax);
        }
    }
}