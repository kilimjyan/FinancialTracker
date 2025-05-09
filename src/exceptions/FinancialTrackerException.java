package exceptions;

// Base custom exception for the application
public class FinancialTrackerException extends Exception {
    public FinancialTrackerException(String message) {
        super(message);
    }
}