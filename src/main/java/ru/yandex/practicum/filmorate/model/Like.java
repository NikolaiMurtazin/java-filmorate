package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a "like" interaction entity.
 * <p>
 * This simple data object links a specific user to a specific film they have liked.
 * It is typically used for data transfer or mapping database records.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {

    /**
     * The unique identifier of the film receiving the like.
     * <p>
     * Uses the {@link Long} wrapper to ensure the value is present (not null).
     * </p>
     */
    @NotNull
    private Long filmId;

    /**
     * The unique identifier of the user who liked the film.
     * <p>
     * Uses the {@link Long} wrapper to ensure the value is present (not null).
     * </p>
     */
    @NotNull
    private Long userId;
}