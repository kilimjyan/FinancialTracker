package models;

import exceptions.InsufficientFundsException;

public class Cash extends PaymentType{

    public Cash() {
        super();
    }

    @Override
    public void add(int money) {
        setIncome(getIncome() + money);
        setBalance(getBalance() + money);
    }

    @Override
    public void deduct(int money) throws InsufficientFundsException {
        if (getBalance() < money) {
            throw new InsufficientFundsException("Not enough funds to deduct");
        }
        setExpense(getExpense() + money);
        setBalance(getBalance() - money);
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
        return "Cash";
    }


    public Cash(Cash other) {
        super();
        this.setBalance(other.getBalance());
        this.setIncome(other.getIncome());
        this.setExpense(other.getExpense());
        this.setSavings(other.getSavings());
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null)
            return false;
        else if (getClass() != otherObject.getClass())
            return false;
        else {
            return super.equals(otherObject);
        }
    }



}