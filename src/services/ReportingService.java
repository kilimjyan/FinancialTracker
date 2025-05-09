package services;
import java.util.*;
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
            report.append("  Total Income: ").append(totalIncome).append("\n");
            report.append("  Total Expenses: ").append(totalExpense).append("\n");
            report.append("  Total Savings: ").append(totalSavings).append("\n");
            report.append("  Total Balance: ").append(method.getBalance()).append("\n");
            if(method instanceof BankTransfer){
                report.append("  Tax Paid: ").append(((BankTransfer)method).getTotalTax()).append("\n");
            }
            if (method instanceof CreditCard) {
                report.append("  Cashback Earned: ").append(((CreditCard)method).getTotalCashback()).append("\n");
            }
            report.append("---------------------------------\n");
        }

        System.out.println(report.toString());
    }
}