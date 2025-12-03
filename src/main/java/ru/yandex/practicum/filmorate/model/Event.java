package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Represents an event in the user's activity feed.
 * <p>
 * This entity captures details about significant actions performed by users,
 * such as adding a friend, liking a film, or writing a review. These events are
 * collected to generate a news feed for users.
 * </p>
 */
@Data
@Builder
public class Event {

    /**
     * The unique identifier of the event.
     */
    private Long eventId;

    /**
     * The timestamp of when the event occurred (in milliseconds since epoch).
     */
    @NotNull
    private Long timestamp;

    /**
     * The ID of the user who triggered the event.
     */
    @NotNull
    private Long userId;

    /**
     * The type of the event (e.g., LIKE, REVIEW, FRIEND).
     */
    @NotNull // Changed from @NotBlank, as @NotBlank is only for Strings
    private EventType eventType;

    /**
     * The operation performed (e.g., ADD, REMOVE, UPDATE).
     */
    @NotNull // Changed from @NotBlank
    private Operation operation;

    /**
     * The ID of the entity involved in the event.
     * <p>
     * Depending on the {@code eventType}, this could be a film ID (for likes/reviews)
     * or another user's ID (for friend additions).
     * </p>
     */
    @NotNull
    private Long entityId;
}