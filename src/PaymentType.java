public abstract class PaymentType {
    private int income;
    private int expense;
    private int savings;
    private int balance;
    private int cashback;
    private int tax;
    private int commission;

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getTax() {
        return tax;
    }
    public void setCommission(int tax) {
        this.tax = tax;
    }

    public int getCommission() {
        return tax;
    }


    public int getCashback(){
        return cashback;
    }

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

    public void setCashback(int cashback) {
        this.cashback = cashback;
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

}

