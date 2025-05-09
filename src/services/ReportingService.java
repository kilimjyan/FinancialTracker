package services;

import models.*;

import java.util.List;

public class ReportingService {
    public void generateMonthlyReport(List<Transaction> transactions, User user) {
        StringBuilder report = new StringBuilder();
        report.append("Monthly Report by Payment Method\n");
        report.append("---------------------------------\n");

        for (PaymentType method : user.getPaymentMethods()) {
            int totalIncome = 0;
            int totalExpense = 0;
            int totalSavings = method.getSavings();

            for (Transaction transaction : transactions) {
                if (transaction.getPaymentType().equals(method) &&
                        transaction.getStatus() == Transaction.TransactionStatus.SUCCESSFUL) {

                    // Categorize based on transaction type
                    if (transaction.getType().equals("add")) {
                        totalIncome += transaction.getAmount();
                    } else if (transaction.getType().equals("deduct")) {
                        totalExpense += transaction.getAmount();
                    } else if (transaction.getType().equals("save")) {
                        totalSavings += transaction.getAmount();
                    }
                }
            }

            report.append("\nPayment Method: ").append(method.toString()).append("\n");
            report.append("  Total Income: ").append(totalIncome).append("\n");
            report.append("  Total Expenses: ").append(totalExpense).append("\n");
            report.append("  Total Savings: ").append(totalSavings).append("\n");
            report.append("  Total Balance: ").append(method.getBalance()).append("\n");
            if(method instanceof BankTransfer){
                report.append("  Tax Paid: ").append(((BankTransfer)method).getTax()).append("\n");
            }
            if (method instanceof CreditCard) {
                report.append("  Cashback Earned: ").append(((CreditCard)method).getCashback()).append("\n");
            }
            report.append("---------------------------------\n");
        }

        System.out.println(report.toString());
    }
}