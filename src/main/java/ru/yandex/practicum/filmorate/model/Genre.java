package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a film genre.
 * <p>
 * This entity serves as a reference data object for classifying films
 * (e.g., Comedy, Drama, Thriller). Equality is determined solely by the {@code id}.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Genre {

    /**
     * The unique identifier of the genre.
     * <p>
     * Uses the wrapper class {@link Integer} to allow for nullability checks during validation.
     * </p>
     */
    private Integer id;

    /**
     * The name of the genre.
     * <p>
     * This field is mandatory and must not exceed 50 characters.
     * </p>
     */
    @NotBlank
    @Size(max = 50)
    private String name;
}