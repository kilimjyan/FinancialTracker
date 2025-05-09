package exceptions;

public class ValidationException extends FinancialTrackerException {
    public ValidationException() {
        super("Validation error occurred.");
    }

    public ValidationException(String message) {
        super(message);
    }
}