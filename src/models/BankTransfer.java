package models;

public class BankTransfer extends PaymentType {
    private int tax;
    private int totalTax;

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


    @Override
    public void add(int money) {
        setIncome(getIncome() + money - generateTax(money));
        setTax(0);
        setBalance(getBalance() + money - generateTax(money));
    }

    @Override
    public void deduct(int money) {
        if (getBalance() < money + generateTax(money)) {
            throw new IllegalStateException("Not enough funds to deduct");
        }
        setExpense(getExpense() + money + generateTax(money));
        setTax(0);
        setBalance(getBalance() - money - generateTax(money));
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
        return "Bank Transfer";
    }

    public int generateTax(int money) {
        if (money > 100000) {
            setTax(getTax() + (int) (money * 0.05));
            totalTax += getTax();
        }
        return getTax();
    }
     @Override
     public void printCurrentBalance() {
         System.out.println("Current balance: " + getBalance());
         System.out.println("Income: " + getIncome());
         System.out.println("Expense: " + getExpense());
         System.out.println("Savings: " + getSavings());
         System.out.println("Tax: " + totalTax);
     }




}