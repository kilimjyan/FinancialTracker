
public class CreditCard extends PaymentType implements PaymentFunctionality{
    @Override
    public void add(int money) {
        setIncome(getIncome() + money);
        setBalance(getBalance() + money);
    }
    @Override
    public void deduct(int money){
        if (money > 50000){
            setExpense(getExpense() + money - generateCashback(money));
            setBalance(getBalance() - (int)(money * 0.95));
        } //cashback
        else{
            setExpense(getExpense() + money);
            setBalance(getBalance() - money);
        }

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

    public int generateCashback(int money) {
        if (money > 50000) {
            setCashback(getCashback() + (int) (money * 0.05));
        }
        return getCashback();
    }



}
