package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotation.FilmReleaseDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Validator implementation for the {@link FilmReleaseDate} annotation.
 * <p>
 * Checks if a film's release date is not earlier than a specific historical date
 * (typically December 28, 1895 - the birth of cinema).
 * </p>
 */
public class ReleaseDateValidator implements ConstraintValidator<FilmReleaseDate, LocalDate> {

    private LocalDate minimumDate;

    /**
     * Initializes the validator.
     * <p>
     * Parses the date string provided in the annotation once during initialization
     * to avoid performance overhead during validation.
     * </p>
     *
     * @param constraintAnnotation the annotation instance containing the date parameter
     */
    @Override
    public void initialize(FilmReleaseDate constraintAnnotation) {
        // Optimization: Parse the date only once at startup
        minimumDate = LocalDate.parse(constraintAnnotation.date(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    /**
     * Validates the release date.
     *
     * @param releaseDate               the date to validate
     * @param constraintValidatorContext context in which the constraint is evaluated
     * @return {@code true} if the date is null (to follow standard "@NotNull" separation)
     * or if the date is on or after the minimum allowed date; {@code false} otherwise.
     */
    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext constraintValidatorContext) {
        // Best Practice: Allow nulls here. If the field is mandatory, use @NotNull in the model.
        if (releaseDate == null) {
            return true;
        }

        // Logic: The date is valid if it is NOT before the minimum date (inclusive check)
        return !releaseDate.isBefore(minimumDate);
    }
}