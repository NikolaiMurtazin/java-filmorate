package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validation.ReleaseDateValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to validate that a film's release date is not earlier than a specific date.
 * <p>
 * The default minimum date is December 28, 1895 (the date of the first public movie screening).
 * </p>
 *
 * @see ReleaseDateValidator
 */
@Documented
@Constraint(validatedBy = ReleaseDateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface FilmReleaseDate {

    /**
     * The error message to be returned if the validation fails.
     *
     * @return the error message template
     */
    String message() default "Film release date must be after {date}";

    /**
     * Allows the specification of validation groups, to which this constraint belongs.
     *
     * @return the array of groups
     */
    Class<?>[] groups() default {};

    /**
     * Payloads can be assigned to a constraint to provide custom metadata.
     *
     * @return the array of payloads
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * The minimum allowed date in "dd-MM-yyyy" format.
     * Default is the "Birthday of Cinema".
     *
     * @return the minimum date string
     */
    String date() default "28-12-1895";
}