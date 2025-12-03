package ru.yandex.practicum.filmorate.repository.feed;

import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;

/**
 * Repository interface for managing user activity feed events.
 * <p>
 * Provides methods to persist events related to user actions (like adding friends,
 * liking films, etc.) to the underlying storage.
 * </p>
 */
public interface FeedRepository {

    /**
     * Records a new event in the user's activity feed.
     * <p>
     * This method is typically called as a side effect of other operations
     * (e.g., when a user adds a friend, an event is saved here).
     * The timestamp of the event should be generated automatically by the implementation.
     * </p>
     *
     * @param userId    the ID of the user who performed the action
     * @param operation the type of operation performed (e.g., ADD, REMOVE, UPDATE)
     * @param eventType the type of event (e.g., LIKE, REVIEW, FRIEND)
     * @param entityId  the ID of the entity involved in the event (e.g., film ID, friend's user ID)
     */
    void saveEvent(long userId, Operation operation, EventType eventType, long entityId);
}
