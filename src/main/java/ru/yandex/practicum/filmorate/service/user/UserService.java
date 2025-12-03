package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.Event;

import java.util.Collection;

/**
 * Service interface for managing {@link User} business logic.
 * <p>
 * This interface defines operations for user profile management,
 * social graph interactions (friending), recommendation systems, and activity feeds.
 * </p>
 */
public interface UserService {

    /**
     * Retrieves all registered users.
     *
     * @return a collection of all users
     */
    Collection<User> getAll();

    /**
     * Retrieves a specific user by their ID.
     *
     * @param userId the ID of the user
     * @return the requested user
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the user is not found
     */
    User getById(long userId);

    /**
     * Creates a new user.
     * <p>
     * If the user's name is empty or null, the business logic typically
     * sets the name to be equal to the login.
     * </p>
     *
     * @param user the user object to create
     * @return the created user with a generated ID
     */
    User create(User user);

    /**
     * Updates an existing user's profile.
     *
     * @param user the user object with updated data
     * @return the updated user
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the user ID does not exist
     */
    User update(User user);

    /**
     * Deletes a user by their ID.
     * <p>
     * This operation typically cascades to remove the user's likes, friendships, and reviews.
     * </p>
     *
     * @param userId the ID of the user to delete
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the user does not exist
     */
    void delete(Long userId);

    /**
     * Adds a friendship link between two users.
     * <p>
     * This action usually triggers an event creation (ADD FRIEND) in the feed.
     * </p>
     *
     * @param userId   the ID of the user adding a friend
     * @param friendId the ID of the user to be added as a friend
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if either user does not exist
     */
    void addFriend(long userId, long friendId);

    /**
     * Removes a friendship link between two users.
     * <p>
     * This action usually triggers an event creation (REMOVE FRIEND) in the feed.
     * </p>
     *
     * @param userId   the ID of the user removing the friend
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
     * Retrieves users who are friends with both specified users.
     *
     * @param userId  the ID of the first user
     * @param otherId the ID of the second user
     * @return a collection of mutual friends
     */
    Collection<User> getCommonFriends(long userId, long otherId);

    /**
     * Generates film recommendations for a user.
     * <p>
     * Based on the user's likes, finds users with similar tastes and suggests
     * films those similar users have liked but the target user has not seen.
     * </p>
     *
     * @param userId the ID of the user to get recommendations for
     * @return a collection of recommended films
     */
    Collection<Film> getFilmRecommendations(long userId);

    /**
     * Retrieves the activity feed for a user.
     *
     * @param userId the ID of the user
     * @return a collection of events (likes, reviews, friendships) associated with the user
     */
    Collection<Event> getFeed(long userId);
}