package exceptions;

// Custom exception for CSV export errors
public class CSVExportException extends FinancialTrackerException {
    public CSVExportException(String message) {
        super(message);
    }
}