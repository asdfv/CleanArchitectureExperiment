package by.grodno.vasili.domain.error;

/**
 * Error while entity not found
 */
public class NotFoundError extends Throwable {
    public NotFoundError(String message) {
        super(message);
    }
}
