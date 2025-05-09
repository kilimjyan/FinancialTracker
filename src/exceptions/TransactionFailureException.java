package exceptions;

// Custom exception for transaction-related errors
public class TransactionFailureException extends FinancialTrackerException {
    public TransactionFailureException(String message) {
        super(message);
    }
}