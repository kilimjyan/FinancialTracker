package exceptions;

public class InsufficientFundsException extends FinancialTrackerException {
    public InsufficientFundsException() {
        super("Insufficient funds for the operation.");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}