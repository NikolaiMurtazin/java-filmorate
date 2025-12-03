package ru.yandex.practicum.filmorate.model;

/**
 * Enumeration of the various types of events that can occur within the application.
 * <p>
 * These types are used to classify user activities recorded in the event feed.
 * </p>
 */
public enum EventType {

    /**
     * Represents an event where a user adds or removes a like from a film (or potentially a review).
     */
    LIKE,

    /**
     * Represents an event involving film reviews (creation, update, or deletion).
     */
    REVIEW,

    /**
     * Represents a social connection event, such as adding or removing a friend.
     */
    FRIEND
}
