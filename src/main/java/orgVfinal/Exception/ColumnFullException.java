package orgVfinal.Exception;

/**
 * Exception thrown when a column is full
 */
public class ColumnFullException extends RuntimeException {
    public ColumnFullException(String message) {
        super(message);
    }
}
