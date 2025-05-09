package exceptions;

public class CSVExportException extends FinancialTrackerException {
    public CSVExportException() {
        super("An error occurred while exporting to CSV.");
    }

    public CSVExportException(String message) {
        super(message);
    }
}