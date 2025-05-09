package models;

public class CreditCard extends PaymentType {
    private String bankName;
    private String creditCardId;
    private int cashback;
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
        if (money > 50000) {
            setExpense(getExpense() + money - generateCashback(money));
            setBalance(getBalance() - (int) (money * 0.95));
        } else {
            setExpense(getExpense() + money);
            setBalance(getBalance() - money);
        }
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
        return "Credit Card: " + this.bankName;
    }


    public int generateCashback(int money) {
        if (money > 50000) {
            setCashback(getCashback() + (int) (money * 0.05));
        }
        return getCashback();
    }

    @Override
    public void printCurrentBalance() {
        System.out.println("Current balance: " + getBalance());
        System.out.println("Income: " + getIncome());
        System.out.println("Savings: " + getSavings());
        System.out.println("Cashback: " + cashback);
    }


}