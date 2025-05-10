package FinancialTracker;

import exceptions.*;
import models.*;
import services.ReportingService;
import utils.CSVUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
public class FinancialTrackerGUI extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPanel mainPanel;
    private User user;
    private ArrayList<Transaction> transactions;
    private ReportingService reportingService;

    public FinancialTrackerGUI() {
        setTitle("Financial Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.PINK);

        mainPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        mainPanel.add(usernameField);

        mainPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        mainPanel.add(emailField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                if (username.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(FinancialTrackerGUI.this, "Please enter both username and email.");
                } else if (!isEmailValid(email)) {
                    JOptionPane.showMessageDialog(FinancialTrackerGUI.this, "Invalid email address. Please try again.");
                } else {
                    user = new User(username, email);
                    user.addPaymentMethod(new BankTransfer());
                    user.addPaymentMethod(new Cash());
                    transactions = new ArrayList<>();
                    reportingService = new ReportingService();
                    JOptionPane.showMessageDialog(FinancialTrackerGUI.this, "Welcome, " + username + "!");
                    showMainMenu();
                }
            }
        });
        mainPanel.add(submitButton);

        add(mainPanel);
    }

    private void showMainMenu() {
        mainPanel.removeAll();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JButton addButton = new JButton("Add Money");
        JButton deductButton = new JButton("Deduct Money");
        JButton saveButton = new JButton("Save Money");
        JButton printButton = new JButton("Print Current Balances");
        JButton reportButton = new JButton("Generate Monthly Report");
        JButton exportButton = new JButton("Export Transactions to CSV");
        JButton addCardButton = new JButton("Add a new credit card");
        JButton exitButton = new JButton("Exit");

        addButton.addActionListener(e -> handleTransaction(false, "add"));
        deductButton.addActionListener(e -> handleTransaction(true, "deduct"));
        saveButton.addActionListener(e -> handleTransaction(true, "save"));
        printButton.addActionListener(e -> printCurrentBalances());
        reportButton.addActionListener(e -> generateMonthlyReport());
        exportButton.addActionListener(e -> exportTransactionsToCSV());
        addCardButton.addActionListener(e -> addNewCreditCard());
        exitButton.addActionListener(e -> System.exit(0));

        mainPanel.add(addButton);
        mainPanel.add(deductButton);
        mainPanel.add(saveButton);
        mainPanel.add(printButton);
        mainPanel.add(reportButton);
        mainPanel.add(exportButton);
        mainPanel.add(addCardButton);
        mainPanel.add(exitButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void handleTransaction(boolean isExpense, String action) {
        PaymentType selectedPaymentMethod = selectPaymentMethod();
        if (selectedPaymentMethod == null) {
            return;
        }

        String amountStr = JOptionPane.showInputDialog(this, "Enter amount to " + action + ":");
        if (amountStr == null || amountStr.isEmpty()) {
            return;
        }

        try {
            int amount = Integer.parseInt(amountStr);
            if (amount <= 0) {
                throw new ValidationException("Amount must be greater than zero.");
            }

            Transaction transaction = new Transaction("TXN" + System.currentTimeMillis(), selectedPaymentMethod, amount);
            transaction.executeTransaction(isExpense);
            transaction.setType(action);
            transactions.add(transaction);
            if(transaction.getStatus() == Transaction.TransactionStatus.SUCCESSFUL)
                JOptionPane.showMessageDialog(this, "Transaction successful! " + (isExpense ? "Deducted " : "Added ") + amount + " AMD" + (isExpense ? " from " : " to ") + selectedPaymentMethod);
            else{
                JOptionPane.showMessageDialog(this, "Transaction failed.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.");
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (FinancialTrackerException e) {
            JOptionPane.showMessageDialog(this, "Transaction failed: " + e.getMessage());
        }
    }

    private PaymentType selectPaymentMethod() {
        ArrayList<PaymentType> methods = user.getPaymentMethods();
        ArrayList<PaymentType> creditCards = new ArrayList<>();
        for (PaymentType method : methods) {
            if (method instanceof CreditCard) {
                creditCards.add(method);
            }
        }

        String[] options = new String[methods.size()];
        for (int i = 0; i < methods.size(); i++) {
            options[i] = methods.get(i).toString();
        }

        int choice = JOptionPane.showOptionDialog(this, "Select a payment method:", "Payment Method", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == -1) {
            return null;
        }

        return methods.get(choice);
    }

    private void addNewCreditCard() {
        String bankName = JOptionPane.showInputDialog(this, "Enter your bank's name for the new credit card:");
        if (bankName == null || bankName.isEmpty()) {
            return;
        }

        String creditCardId = JOptionPane.showInputDialog(this, "Enter your credit card ID:");
        if (creditCardId == null || creditCardId.isEmpty()) {
            return;
        }

        try {
            if (creditCardId.length() != 16) {
                throw new InvalidCreditCardException("Credit card ID must be exactly 16 digits long.");
            }
            if (!creditCardId.matches("\\d+")) {
                throw new InvalidCreditCardException("Credit card ID must contain only digits.");
            }
            

            for (PaymentType method : user.getPaymentMethods()) {
                if (method instanceof CreditCard && ((CreditCard) method).getCreditCardId().equals(creditCardId) && ((CreditCard) method).getBankName().equals(bankName)) {
                    throw new DuplicateCreditCardException("A credit card with ID " + creditCardId + "from" + ((CreditCard) method).getBankName() +" already exists.");
                }
            }
            
            user.addPaymentMethod(new CreditCard(bankName, creditCardId));
            JOptionPane.showMessageDialog(this, "Credit card for " + bankName + " added successfully.");
        } catch (InvalidCreditCardException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Invalid Credit Card", JOptionPane.ERROR_MESSAGE);
        } catch (DuplicateCreditCardException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Duplicate Credit Card", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printCurrentBalances() {
        StringBuilder message = new StringBuilder("Current Balances:\n");
        for (PaymentType method : user.getPaymentMethods()) {
            message.append("Payment method: ").append(method).append("\n");
            message.append("  Current balance: ").append(method.getBalance()).append(" AMD\n");
            message.append("  Income: ").append(method.getIncome()).append(" AMD\n");
            message.append("  Expenses: ").append(method.getExpense()).append(" AMD\n");
            message.append("  Savings: ").append(method.getSavings()).append(" AMD\n");
            if(method instanceof BankTransfer){
                message.append("  Tax Paid: ").append(((BankTransfer)method).getTotalTax()).append(" AMD\n");
            }
            if (method instanceof CreditCard) {
                message.append("  Cashback Earned: ").append(((CreditCard)method).getTotalCashback()).append(" AMD\n");
            }
        }
        JOptionPane.showMessageDialog(this, message.toString());
    }

    private void generateMonthlyReport() {
        StringBuilder report = new StringBuilder();
        report.append("Monthly Report of " + LocalDate.now().getMonth() + " " + LocalDate.now().getYear() + "\n");
        report.append("---------------------------------\n");


        for (PaymentType method : user.getPaymentMethods()) {
            int totalIncome = 0;
            int totalExpense = 0;
            int totalSavings = method.getSavings();

            for (Transaction transaction : transactions) {
                if (transaction.getPaymentType().equals(method) &&
                        transaction.getStatus() == Transaction.TransactionStatus.SUCCESSFUL) {


                    if (transaction.getType().equals("add")) {
                        totalIncome += transaction.getPaymentType().getIncome();
                    } else if (transaction.getType().equals("deduct")) {
                        totalExpense += transaction.getPaymentType().getExpense();
                    } else if (transaction.getType().equals("save")) {
                        totalSavings += transaction.getPaymentType().getSavings();
                    }
                }
            }

            report.append("\nPayment Method: ").append(method.toString()).append("\n");
            report.append("  Total Income: ").append(totalIncome).append(" AMD\n");
            report.append("  Total Expenses: ").append(totalExpense).append(" AMD\n");
            report.append("  Total Savings: ").append(totalSavings).append(" AMD\n");
            report.append("  Total Balance: ").append(method.getBalance()).append(" AMD\n");
            if(method instanceof BankTransfer){
                report.append("  Tax Paid: ").append(((BankTransfer)method).getTotalTax()).append(" AMD\n");
            }
            if (method instanceof CreditCard) {
                report.append("  Cashback Earned: ").append(((CreditCard)method).getTotalCashback()).append(" AMD\n");
            }
            report.append("---------------------------------\n");
        }

        JOptionPane.showMessageDialog(this, report.toString(), "Monthly Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportTransactionsToCSV() {
        String fileName = JOptionPane.showInputDialog(this, "Enter file name for CSV export:");
        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        try {
            CSVUtil.writeTransactionsToCSV(transactions, fileName);
            JOptionPane.showMessageDialog(this, "Transactions exported to " + fileName + ".csv successfully.");
        } catch (CSVExportException e) {
            JOptionPane.showMessageDialog(this, "Error exporting transactions: " + e.getMessage());
        }
    }

    private boolean isEmailValid(String mail) {
        if (mail == null || mail.isEmpty()) {
            return false;
        }
        if (!mail.contains(".")) {
            return false;
        }
        if (!mail.contains("@")) {
            return false;
        }
        int indexAt = mail.lastIndexOf("@");
        int indexDot = mail.lastIndexOf(".");
        if (indexAt == -1 || indexDot == -1 || indexAt >= indexDot) {
            return false;
        }
        String localPart = mail.substring(0, indexAt);
        String domain = mail.substring(indexAt + 1, indexDot);
        String tld = mail.substring(indexDot + 1);

        return !localPart.isEmpty() && !domain.isEmpty() && !tld.isEmpty();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FinancialTrackerGUI().setVisible(true);
            }
        });
    }
}
