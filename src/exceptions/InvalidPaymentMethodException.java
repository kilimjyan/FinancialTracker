package exceptions;

// Custom exception for invalid payment methods
public class InvalidPaymentMethodException extends FinancialTrackerException {
    public InvalidPaymentMethodException(String message) {
        super(message);
    }
}