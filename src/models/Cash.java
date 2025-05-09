package models;

public class Cash extends PaymentType{

    @Override
    public void add(int money) {
        setIncome(getIncome() + money);
        setBalance(getBalance() + money);
    }

    @Override
    public void deduct(int money) {
        if (getBalance() < money) {
            throw new IllegalStateException("Not enough funds to deduct");
        }
        setExpense(getExpense() + money);
        setBalance(getBalance() - money);
    }

    @Override
    public void save(int money) {
        if (getBalance() < money) {
            throw new IllegalStateException("Not enough funds to save");
        }
        setSavings(getSavings() + money);
        setBalance(getBalance() - money);
    }


    @Override
    public String toString() {
        return "Cash";
    }



}