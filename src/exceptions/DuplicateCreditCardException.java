package exceptions;

public class DuplicateCreditCardException extends FinancialTrackerException {
    public DuplicateCreditCardException() {
        super("A credit card with this ID already exists.");
    }

    public DuplicateCreditCardException(String message) {
        super(message);
    }
} 