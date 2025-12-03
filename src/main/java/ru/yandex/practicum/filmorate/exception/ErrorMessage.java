package ru.yandex.practicum.filmorate.exception;

/**
 * Data Transfer Object (DTO) representing an error response body.
 *
 * @param error the description of the error
 */
public record ErrorMessage(String error) {
}
