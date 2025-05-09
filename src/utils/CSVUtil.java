package utils;

import exceptions.CSVExportException;
import models.Transaction;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVUtil {
    public static void writeTransactionsToCSV(ArrayList<Transaction> transactions, String fileName) throws CSVExportException {
        if (transactions == null || transactions.isEmpty()) {
            throw new CSVExportException("No transactions available to export.");
        }

        try (PrintWriter outputStream = new PrintWriter(new FileOutputStream(fileName + ".csv"))) {
            System.out.println("Writing to file.");

            // Write header
            outputStream.println("Transaction ID , Payment Method, Amount, Date , Status");

            // Write transactions
            for (Transaction transaction : transactions) {
                outputStream.println(transaction.toCSV());
            }

            System.out.println("Transactions successfully written to " + fileName);
        } catch (FileNotFoundException e) {
            throw new CSVExportException("Error opening the file " + fileName + ": " + e.getMessage());
        }
    }
}