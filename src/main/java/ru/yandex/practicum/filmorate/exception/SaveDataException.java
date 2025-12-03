package ru.yandex.practicum.filmorate.exception;

/**
 * Exception thrown when an error occurs while saving or persisting data.
 * <p>
 * This runtime exception indicates that a data storage operation (insert, update) failed,
 * typically due to database constraint violations, connection issues, or other persistence layer errors.
 * </p>
 */
public class SaveDataException extends RuntimeException {

    /**
     * Constructs a new SaveDataException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the failure
     */
    public SaveDataException(String message) {
        super(message);
    }

    /**
     * Constructs a new SaveDataException with the specified detail message and cause.
     * <p>
     * Use this constructor when you want to wrap a lower-level exception (e.g., SQLException)
     * without losing the original stack trace.
     * </p>
     *
     * @param message the detail message
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public SaveDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
