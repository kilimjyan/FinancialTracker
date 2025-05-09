package utils;

import exceptions.CSVExportException;
import models.Transaction;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

public class CSVUtil {
    public static void writeTransactionsToCSV(List<Transaction> transactions, String fileName) throws CSVExportException {
        if (transactions == null || transactions.isEmpty()) {
            throw new CSVExportException("No transactions available to export.");
        }

        try (PrintWriter outputStream = new PrintWriter(new FileOutputStream(fileName + ".csv"))) {
            System.out.println("Writing to file.");


            outputStream.println("Transaction ID , Payment Method, Amount, Date , Status");


            for (Transaction transaction : transactions) {
                outputStream.println(transaction.toCSV());
            }

            System.out.println("Transactions successfully written to " + fileName);
        } catch (FileNotFoundException e) {
            throw new CSVExportException("Error opening the file " + fileName + ": " + e.getMessage());
        }
    }
}