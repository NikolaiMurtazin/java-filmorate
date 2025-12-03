package ru.yandex.practicum.filmorate.exception;

/**
 * Exception thrown when a requested resource (e.g., User, Film, Genre) cannot be found.
 * <p>
 * This exception is typically handled by the global error handler
 * ({@link ru.yandex.practicum.filmorate.exception.ErrorHandler}), resulting in an HTTP 404 Not Found response.
 * </p>
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message the detail message explaining which resource was not found
     */
    public NotFoundException(String message) {
        super(message);
    }
}
