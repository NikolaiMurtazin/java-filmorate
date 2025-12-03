package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.FilmReleaseDate;

import java.time.LocalDate;
import java.util.LinkedHashSet;

/**
 * Represents a film entity in the application.
 * <p>
 * Contains all essential details about a movie, including its metadata
 * like MPA rating, genres, and directors.
 * </p>
 */
@Data
@Builder
public class Film {

    /**
     * The unique identifier of the film.
     */
    private Long id;

    /**
     * The name (title) of the film.
     * <p>
     * This field is mandatory and cannot be blank.
     * </p>
     */
    @NotBlank
    @Size(max = 255)
    private String name;

    /**
     * A short description or synopsis of the film.
     * <p>
     * The description length is limited to 200 characters.
     * </p>
     */
    @NotBlank
    @Size(max = 200)
    private String description;

    /**
     * The release date of the film.
     * <p>
     * Validated by {@link FilmReleaseDate} to ensure it is not earlier than the birth of cinema (December 28, 1895).
     * </p>
     */
    @NotNull
    @FilmReleaseDate
    private LocalDate releaseDate;

    /**
     * The duration of the film in minutes.
     * <p>
     * Must be a positive integer.
     * </p>
     */
    @Positive
    private int duration;

    /**
     * The MPA (Motion Picture Association) rating of the film (e.g., G, PG, R).
     */
    @NotNull
    private Mpa mpa;

    /**
     * The set of genres associated with the film.
     * <p>
     * Uses {@link LinkedHashSet} to preserve the order of genres (e.g., as entered by the user).
     * </p>
     */
    @Builder.Default // Ensures the list is initialized even when using the Builder
    private LinkedHashSet<Genre> genres = new LinkedHashSet<>();

    /**
     * The set of directors who directed the film.
     * <p>
     * Uses {@link LinkedHashSet} to preserve order.
     * </p>
     */
    @Builder.Default // Ensures the list is initialized even when using the Builder
    private LinkedHashSet<Director> directors = new LinkedHashSet<>();
}