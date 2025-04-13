public class Cash extends PaymentType implements PaymentFunctionality{

        @Override
        public void add(int money) {
            setIncome(getIncome() + money);
            setBalance(getBalance() + money);
        }
        @Override
        public void deduct(int money){
            setExpense(getExpense() + money);
            setBalance(getBalance() - money);
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
    }


