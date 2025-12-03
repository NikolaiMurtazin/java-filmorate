package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global error handler for the application.
 * <p>
 * This class serves as a centralized exception handling mechanism using {@link RestControllerAdvice}.
 * It catches exceptions thrown by any controller, logs them, and returns a structured
 * {@link ErrorMessage} response with the appropriate HTTP status code.
 * </p>
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Handles {@link NotFoundException}.
     * <p>
     * Triggered when a requested resource (like a User or Film) cannot be found in the storage.
     * Returns HTTP 404 Not Found.
     * </p>
     *
     * @param exception the exception containing the error details
     * @return a response entity with the error message and 404 status
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFound(NotFoundException exception) {
        log.error("Resource not found: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    /**
     * Handles validation errors and illegal arguments.
     * <p>
     * Triggered when input data fails validation constraints (e.g., @Valid checks)
     * or when an invalid argument is passed to a method.
     * Returns HTTP 400 Bad Request.
     * </p>
     *
     * @param exception the exception thrown
     * @return a response entity with the error message and 400 status
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> handleValidationException(Exception exception) {
        log.error("Validation error: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    /**
     * Handles data persistence errors.
     * <p>
     * Triggered when an error occurs while saving data to the database
     * (e.g., unique constraint violations).
     * Returns HTTP 500 Internal Server Error.
     * </p>
     *
     * @param exception the exception thrown
     * @return a response entity with the error message and 500 status
     */
    @ExceptionHandler(SaveDataException.class)
    public ResponseEntity<ErrorMessage> handleSaveData(SaveDataException exception) {
        log.error("Data saving error: {}", exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(exception.getMessage()));
    }

    /**
     * Fallback handler for all unexpected exceptions.
     * <p>
     * Catches any exception not handled by specific methods above.
     * Returns HTTP 500 Internal Server Error to prevent leaking stack traces to the client.
     * </p>
     *
     * @param exception the unhandled exception
     * @return a response entity with the error message and 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleInternalError(Exception exception) {
        log.error("Internal server error: ", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage("An unexpected error occurred."));
    }
}