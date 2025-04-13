public class BankTransfer extends PaymentType implements PaymentFunctionality{

    @Override
    public void add(int money) {
        setIncome(getIncome() + money + generateCommission(money) + generateTax(money));
        setBalance(getBalance() + money + generateCommission(money) + generateTax(money));
    }
    @Override
    public void deduct(int money){
        setExpense(getExpense() + money + generateCommission(money) + generateTax(money));
        setBalance(getBalance() - money - generateCommission(money) - generateTax(money));
    }
    @Override
    public void save(int money){
        setSavings(getSavings() + money);
        setBalance(getBalance() - money);
        setSavings(getSavings() + money);

    }
    public void printCurrentBalance(){
        System.out.println(getBalance());
    }

    public int generateTax (int money){
        if (money > 100000) {
            setTax(getTax() + (int) (money * 0.05));
        }
        else setTax(getTax() + (int)(money * 0.04));
        return getCashback();
    }

    public int generateCommission (int money){
        if (money < 100000) {
            setCommission(getCommission() + (int) (money + 400));
        } else  setCommission(getCommission() + (int) (money*0.001));
        return getCashback();
    }

}
