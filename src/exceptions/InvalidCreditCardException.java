package exceptions;

public class InvalidCreditCardException extends FinancialTrackerException {
    public InvalidCreditCardException() {
        super("Invalid credit card ID. The ID must be 16 digits long and contain only numbers.");
    }

    public InvalidCreditCardException(String message) {
        super(message);
    }
} 