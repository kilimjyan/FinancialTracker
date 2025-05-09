package exceptions;

// Custom exception for validation errors
public class ValidationException extends FinancialTrackerException {
    public ValidationException(String message) {
        super(message);
    }
}
