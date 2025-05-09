package models;

import exceptions.InsufficientFundsException;

public class CreditCard extends PaymentType {
    private String bankName;
    private String creditCardId;
    private int cashback;
    private int totalCashback;


    public int getTotalCashback() {
        return totalCashback;
    }

    public void setTotalCashback(int totalCashback) {
        this.totalCashback = totalCashback;
    }

    public String getBankName() {
        return bankName;
    }
    public int getCashback() {
        return cashback;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }



    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCreditCardId() {
        return creditCardId;
    }
    public void setCashback(int cashback) {
        this.cashback = cashback;
    }


    public CreditCard(String bankname, String creditCardId){
        super();
        this.bankName=bankname;
        this.creditCardId=creditCardId;

    }


    public CreditCard(CreditCard other) {
        super();
        this.bankName = other.bankName;
        this.creditCardId = other.creditCardId;
        this.cashback = other.cashback;
        this.totalCashback = other.totalCashback;
        this.setBalance(other.getBalance());
        this.setIncome(other.getIncome());
        this.setExpense(other.getExpense());
        this.setSavings(other.getSavings());
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
        if (money > 50000) {
            int cashbackAmount = generateCashback(money);
            setExpense(getExpense() + money - cashbackAmount);
            setBalance(getBalance() - (money - cashbackAmount));
        } else {
            setExpense(getExpense() + money);
            setBalance(getBalance() - money);
        }
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
        return "Credit Card: " + this.bankName;
    }


    public int generateCashback(int money) {
        if (money > 50000) {
            int cashbackAmount = (int) (money * 0.03);
            setCashback(cashbackAmount);
            totalCashback += cashbackAmount;
            return cashbackAmount;
        }
        return 0;
    }

    @Override
    public void printCurrentBalance() {
        System.out.println("Current balance: " + getBalance());
        System.out.println("Income: " + getIncome());
        System.out.println("Expense: " + getExpense());
        System.out.println("Savings: " + getSavings());
        System.out.println("Cashback: " + totalCashback);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null)
            return false;
        else if (getClass() != otherObject.getClass())
            return false;
        else {
            CreditCard otherCreditCard = (CreditCard)otherObject;
            return (super.equals(otherObject)
                    && bankName.equals(otherCreditCard.bankName)
                    && creditCardId.equals(otherCreditCard.creditCardId)
                    && cashback == otherCreditCard.cashback
                    && totalCashback == otherCreditCard.totalCashback);
        }
    }

}