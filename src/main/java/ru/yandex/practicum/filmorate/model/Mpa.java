package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a Motion Picture Association (MPA) rating.
 * <p>
 * This entity serves as a reference data object for film ratings (e.g., G, PG, PG-13, R, NC-17).
 * Equality is determined solely by the {@code id}.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mpa {

    /**
     * The unique identifier of the MPA rating.
     * <p>
     * Uses the wrapper class {@link Integer} to allow for nullability checks during validation.
     * </p>
     */
    @NotNull
    private Integer id;

    /**
     * The name of the MPA rating (e.g., "PG-13").
     * <p>
     * This field is mandatory and must not exceed 20 characters.
     * </p>
     */
    @NotBlank
    @Size(max = 20)
    private String name;
}