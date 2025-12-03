package ru.yandex.practicum.filmorate.model;

/**
 * Enumeration of operation types that can be performed on entities.
 * <p>
 * These operations classify the specific action taken during an event,
 * such as adding a friend, removing a like, or updating a review.
 * </p>
 */
public enum Operation {

    /**
     * Represents a removal or deletion action (e.g., removing a like, deleting a friend).
     */
    REMOVE,

    /**
     * Represents an addition or creation action (e.g., adding a like, adding a friend).
     */
    ADD,

    /**
     * Represents a modification or update action (e.g., updating the content of a review).
     */
    UPDATE
}