package exceptions;

public class FinancialTrackerException extends Exception {
    public FinancialTrackerException() {
        super("A financial tracker error occurred.");
    }

    public FinancialTrackerException(String message) {
        super(message);
    }

    public FinancialTrackerException(String message, Throwable cause) {
        super(message, cause);
    }
}