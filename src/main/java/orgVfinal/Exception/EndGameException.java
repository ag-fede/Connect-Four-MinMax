package orgVfinal.Exception;

/**
 * Exception thrown when the game is finished because the board is full
 */
public class EndGameException extends RuntimeException {
    public EndGameException(String message) {
        super(message);
    }
}
