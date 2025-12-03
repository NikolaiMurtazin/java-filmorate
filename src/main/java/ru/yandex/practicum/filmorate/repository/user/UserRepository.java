package ru.yandex.practicum.filmorate.repository.user;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.Event;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository interface for managing {@link User} persistence operations.
 * <p>
 * Defines standard CRUD operations for users, as well as methods to manage
 * the social graph (friends) and retrieve user activity feeds.
 * </p>
 */
public interface UserRepository {

    /**
     * Saves a new user to the storage.
     *
     * @param user the user object to be created
     * @return the saved user with its generated ID
     */
    User create(User user);

    /**
     * Updates an existing user's profile information.
     *
     * @param user the user object with updated data
     * @return the updated user
     */
    User update(User user);

    /**
     * Retrieves all registered users from the storage.
     *
     * @return a collection of all users
     */
    Collection<User> getAll();

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the ID of the user to retrieve
     * @return an {@link Optional} containing the user if found, or empty if not
     */
    Optional<User> getById(Long id);

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     */
    void delete(long userId);

    /**
     * Adds a user as a friend to another user.
     *
     * @param userId   the ID of the user adding a friend
     * @param friendId the ID of the user being added
     */
    void addFriend(long userId, long friendId);

    /**
     * Removes a friendship link between two users.
     *
     * @param userId   the ID of the user removing a friend
     * @param friendId the ID of the friend being removed
     */
    void deleteFriend(long userId, long friendId);

    /**
     * Retrieves the list of friends for a specific user.
     *
     * @param userId the ID of the user
     * @return a collection of the user's friends
     */
    Collection<User> getFriends(long userId);

    /**
     * Retrieves the list of mutual friends between two users.
     *
     * @param userId  the ID of the first user
     * @param otherId the ID of the second user
     * @return a collection of users who are friends with both specified users
     */
    Collection<User> getCommonFriends(long userId, long otherId);

    /**
     * Retrieves the activity feed (events) for a specific user.
     * <p>
     * The feed contains a history of actions performed by the user (e.g., likes, reviews).
     * </p>
     *
     * @param userId the ID of the user
     * @return a collection of events associated with the user
     */
    Collection<Event> getFeed(long userId);
}