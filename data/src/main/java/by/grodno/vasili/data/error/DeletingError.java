package by.grodno.vasili.data.error;

/**
 * Error while deleting entity from datasource
 */
public class DeletingError extends Throwable {
    public DeletingError(String message) {
        super(message);
    }
}
