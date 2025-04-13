import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create payment methods
        Cash myCash = new Cash();
        CreditCard myCreditCard = new CreditCard();
        BankTransfer myBankTransfer = new BankTransfer();



        // Add initial funds
        myCash.add(20000);
        myCreditCard.add(100000);
        myBankTransfer.add(150000);

        // Deduct some expenses
        myCash.deduct(5000);
        myCreditCard.deduct(60000); // Should trigger cashback
        myBankTransfer.deduct(80000); // Should apply tax and commission

        // Save some money
        myCash.save(2000);
        myCreditCard.save(5000);
        myBankTransfer.save(10000);

        // Print balances
        System.out.println("----- CURRENT BALANCES -----");
        System.out.print("Cash: ");
        myCash.printCurrentBalance();
        System.out.print("Credit Card: ");
        myCreditCard.printCurrentBalance();
        System.out.print("Bank Transfer: ");
        myBankTransfer.printCurrentBalance();

        // Create transactions
        Transaction t1 = new Transaction("TXN001", myCash, 5000);
        Transaction t2 = new Transaction("TXN002", myCreditCard, 60000);
        Transaction t3 = new Transaction("TXN003", myBankTransfer, 80000);

        // Assign current date and print summary
        LocalDate date = LocalDate.now();
        System.out.println("\n----- TRANSACTIONS -----");
        System.out.println("Transaction ID: " + t1.getTransactionID() + ", Method: Cash, Amount: " + t1.getAmount() + ", Date: " + date);
        System.out.println("Transaction ID: " + t2.getTransactionID() + ", Method: Credit Card, Amount: " + t2.getAmount() + ", Date: " + date);
        System.out.println("Transaction ID: " + t3.getTransactionID() + ", Method: Bank Transfer, Amount: " + t3.getAmount() + ", Date: " + date);
    }
}
