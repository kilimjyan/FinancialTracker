package FinancialTracker;

import exceptions.DuplicateCreditCardException;
import exceptions.FinancialTrackerException;
import exceptions.InvalidCreditCardException;
import models.*;
import services.ReportingService;
import utils.CSVUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class FinancialTrackerDemo {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String usernameInput = null;
        String mailInput = null;

        try {
            System.out.println("Enter your username:");
            usernameInput = scanner.next();

            System.out.println("Enter your email:");
            mailInput = scanner.next();
            while (!isEmailValid(mailInput)) {
                System.out.println("Invalid email. Please try again:");
                mailInput = scanner.next();
            }
        } catch (InputMismatchException e) {
            System.err.println("Invalid input provided. Please restart the application and try again.");
            return;
        }

        User user = new User(usernameInput, mailInput);

        user.addPaymentMethod(new BankTransfer());
        user.addPaymentMethod(new Cash());

        ArrayList<Transaction> transactions = new ArrayList<>();
        ReportingService reportingService = new ReportingService();

        while (true) {
            try {
                System.out.println("\n----- Financial Tracker -----");
                System.out.println("1. Add Money");
                System.out.println("2. Deduct Money");
                System.out.println("3. Save Money");
                System.out.println("4. Print Current Balances");
                System.out.println("5. Generate Monthly Report");
                System.out.println("6. Export Transactions to CSV");
                System.out.println("7. Add a new credit card as your payment method");
                System.out.println("8. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        handleTransaction(scanner, user, transactions, false, "add");
                        break;

                    case 2:
                        handleTransaction(scanner, user, transactions, true, "deduct");
                        break;

                    case 3:
                        System.out.print("Enter amount to save: ");
                        int saveAmount = scanner.nextInt();
                        PaymentType savePaymentMethod = selectPaymentMethod(scanner, user);
                        if (savePaymentMethod != null) {
                            handleTransaction(scanner, user, transactions, true, "save");
                        }
                        break;

                    case 4:
                        printCurrentBalances(user);
                        break;

                    case 5:
                        System.out.println("\nGenerating report for the month of " + LocalDate.now().getMonth());
                        reportingService.generateMonthlyReport(transactions, user);
                        break;

                    case 6:
                        System.out.print("Enter file name for CSV export: ");
                        String fileName = scanner.next();
                        CSVUtil.writeTransactionsToCSV(transactions, fileName);
                        break;

                    case 7:
                        System.out.println("Enter your bank's name for the new credit card:");
                        String bankName = scanner.next();
                        System.out.println("Enter your credit card ID:");
                        String creditCardId = scanner.next();
                        try {
                            if (creditCardId.length() != 16) {
                                throw new InvalidCreditCardException("Credit card ID must be exactly 16 digits long.");
                            }
                            if (!creditCardId.matches("\\d+")) {
                                throw new InvalidCreditCardException("Credit card ID must contain only digits.");
                            }
                            
                            // Check for duplicate credit card
                            for (PaymentType method : user.getPaymentMethods()) {
                                if (method instanceof CreditCard && ((CreditCard) method).getCreditCardId().equals(creditCardId)) {
                                    throw new DuplicateCreditCardException("A credit card with ID " + creditCardId + " already exists.");
                                }
                            }
                            
                            user.addPaymentMethod(new CreditCard(bankName, creditCardId));
                            System.out.println("Credit card for " + bankName + " added successfully.");
                        } catch (InvalidCreditCardException e) {
                            System.err.println(e.getMessage());
                        } catch (DuplicateCreditCardException e) {
                            System.err.println(e.getMessage());
                        }
                        break;

                    case 8:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid option. Please choose a valid option.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input detected. Please enter a valid number.");
                scanner.next();
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());

            }
        }
    }

    private static void handleTransaction(Scanner scanner, User user, ArrayList<Transaction> transactions, boolean isExpense, String action) throws FinancialTrackerException {
        try {
            System.out.println("Please specify the payment method: enter BANK, CARD, or CASH");
            String paymentMethodInput = scanner.next();
            PaymentType selectedPaymentMethod = null;

            if (paymentMethodInput.equalsIgnoreCase("CARD")) {
                ArrayList<PaymentType> creditCards = new ArrayList<>();
                for (PaymentType method : user.getPaymentMethods()) {
                    if (method instanceof CreditCard) {
                        creditCards.add(method);
                    }
                }

                if (creditCards.size() > 1) {
                    System.out.println("You have multiple credit cards. Please select one:");
                    for (int i = 0; i < creditCards.size(); i++) {
                        CreditCard card = (CreditCard) creditCards.get(i);
                        System.out.println((i + 1) + ". " + card.getBankName() + " - " + card.getCreditCardId());
                    }
                    int cardChoice = scanner.nextInt();
                    if (cardChoice < 1 || cardChoice > creditCards.size()) {
                        System.out.println("Invalid choice. Transaction canceled.");
                        return;
                    }
                    selectedPaymentMethod = creditCards.get(cardChoice - 1);
                } else if (creditCards.size() == 1) {
                    selectedPaymentMethod = creditCards.get(0);
                } else {
                    System.out.println("No credit cards available. Please try again.");
                    return;
                }
            } else {
                selectedPaymentMethod = selectPaymentMethodByType(user, paymentMethodInput);
            }

            if (selectedPaymentMethod == null) {
                System.out.println("Invalid or uninitialized payment method. Please try again.");
                return;
            }

            System.out.print("Enter amount to " + action + ": ");
            int amount = scanner.nextInt();
            if (amount <= 0) {
                System.out.println("Amount must be greater than zero. Transaction canceled.");
                return;
            }
            Transaction transaction = new Transaction("TXN" + System.currentTimeMillis(), selectedPaymentMethod, amount);
            transaction.executeTransaction(isExpense);
            transaction.setType(action);
            transactions.add(transaction);

            if(transaction.getStatus() == Transaction.TransactionStatus.SUCCESSFUL)
                System.out.println("Transaction successful! " + (isExpense ? "Deducted " : "Added ") + amount + " AMD" + (isExpense ? " from " : " to ") + paymentMethodInput);
        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Transaction canceled.");
            scanner.next();
        } catch (Exception e) {
            System.err.println("An error occurred while processing the transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static PaymentType selectPaymentMethodByType(User user, String paymentMethodInput) {
        for (PaymentType method : user.getPaymentMethods()) {
            if (method instanceof CreditCard && paymentMethodInput.equalsIgnoreCase("CARD")) {
                return method;
            }
            if (method instanceof BankTransfer && paymentMethodInput.equalsIgnoreCase("BANK")) {
                return method;
            }
            if (method instanceof Cash && paymentMethodInput.equalsIgnoreCase("CASH")) {
                return method;
            }
        }
        return null;
    }

    private static PaymentType selectPaymentMethod(Scanner scanner, User user) {
        try {
            System.out.println("Select a payment method:");
            ArrayList<PaymentType> methods = user.getPaymentMethods();
            for (int i = 0; i < methods.size(); i++) {
                System.out.println((i + 1) + ". " + methods.get(i).getClass().getSimpleName());
            }
            int methodChoice = scanner.nextInt();
            if (methodChoice < 1 || methodChoice > methods.size()) {
                System.out.println("Invalid choice.");
                return null;
            }
            return methods.get(methodChoice - 1);
        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please try again.");
            scanner.next(); // Clear invalid input
            return null;
        }
    }

    private boolean isEmailValid(String mail) {
        if (!(mail.contains("."))) {
            return false;
        }
        if (!(mail.contains("@"))) {
            return false;
        }
        int indexAt = mail.lastIndexOf("@");
        int indexDot = mail.lastIndexOf(".");
        if ((mail.substring(0, indexAt)).isEmpty() || (mail.substring(indexAt + 1, indexDot)).isEmpty()
                || (mail.substring(indexDot + 1)).isEmpty()) {
            return false;
        }
        return true;
    }

    private static void printCurrentBalances(User user) {
        for (PaymentType method : user.getPaymentMethods()) {
            System.out.println("Payment method: " + method);
            System.out.println("Current balance: " + method.getBalance() + " AMD");
            System.out.println("Income: " + method.getIncome() + " AMD");
            System.out.println("Expense: " + method.getExpense() + " AMD");
            System.out.println("Savings: " + method.getSavings() + " AMD");
            if(method instanceof BankTransfer){
                System.out.println("Tax Paid: " + ((BankTransfer)method).getTotalTax() + " AMD");
            }
            if (method instanceof CreditCard) {
                System.out.println("Cashback Earned: " + ((CreditCard)method).getTotalCashback() + " AMD");
            }
            System.out.println();
        }
    }
}