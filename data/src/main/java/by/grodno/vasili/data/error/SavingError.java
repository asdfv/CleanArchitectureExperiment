package by.grodno.vasili.data.error;

/**
 * Error while saving entity into datasource
 */
public class SavingError extends Throwable {
    public SavingError(String message) {
        super(message);
    }
}
