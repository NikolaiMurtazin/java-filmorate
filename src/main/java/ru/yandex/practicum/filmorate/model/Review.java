package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a review for a film.
 * <p>
 * This entity stores the user's opinion (positive or negative) about a specific movie,
 * along with a text commentary and a usefulness rating determined by other users.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    /**
     * The unique identifier of the review.
     */
    private Long reviewId;

    /**
     * The unique identifier of the film being reviewed.
     * <p>
     * Must not be null.
     * </p>
     */
    @NotNull
    private Long filmId;

    /**
     * The unique identifier of the user who wrote the review.
     * <p>
     * Must not be null.
     * </p>
     */
    @NotNull
    private Long userId;

    /**
     * The text content of the review.
     * <p>
     * Must contain at least one non-whitespace character and cannot exceed 200 characters.
     * </p>
     */
    @NotBlank
    @Size(max = 200)
    private String content;

    /**
     * The usefulness rating of the review.
     * <p>
     * Calculated based on likes and dislikes from other users.
     * Default value is usually 0.
     * </p>
     */
    private int useful;

    /**
     * Indicates whether the review is positive or negative.
     * <p>
     * {@code true} for a positive review, {@code false} for a negative one.
     * Uses the wrapper {@link Boolean} to enforce validation (must be explicitly provided).
     * </p>
     */
    @NotNull
    private Boolean isPositive;
}
