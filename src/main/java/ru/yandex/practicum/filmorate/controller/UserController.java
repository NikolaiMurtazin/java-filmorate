package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.Collection;

/**
 * REST Controller for managing {@link User} entities.
 * <p>
 * This controller handles user lifecycle operations (CRUD), social interactions (friends),
 * and personalized user data like activity feeds and film recommendations.
 * </p>
 */
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Retrieves all registered users.
     *
     * @return a collection of all users
     */
    @GetMapping
    public Collection<User> getAll() {
        log.info("GET /users request");
        Collection<User> users = userService.getAll();
        log.info("GET /users response: {}", users.size());
        return users;
    }

    /**
     * Retrieves a specific user by their unique identifier.
     *
     * @param userId the ID of the user to retrieve
     * @return the requested user object
     */
    @GetMapping("/{userId}")
    public User getById(@PathVariable Long userId) {
        log.info("GET /users/{} by ID {} request", userId, userId);
        User user = userService.getById(userId);
        log.info("GET /users/{} response: success {}", userId, user);
        return user;
    }

    /**
     * Creates a new user.
     * <p>
     * The request body must contain valid user data.
     * </p>
     *
     * @param user the user object to be created
     * @return the created user with its assigned ID
     */
    @PostMapping
    public User create(@Validated @RequestBody User user) {
        log.info("POST /users request: {}", user);
        User createdUser = userService.create(user);
        log.info("POST /users response: {}", createdUser);
        return createdUser;
    }

    /**
     * Updates an existing user's details.
     *
     * @param user the user object with updated data
     * @return the updated user object
     */
    @PutMapping
    public User update(@Validated @RequestBody User user) {
        log.info("PUT /users request: {}", user);
        User updateUser = userService.update(user);
        log.info("PUT /users response: {}", updateUser);
        return updateUser;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("DELETE /users/{} request", id);
        userService.delete(id);
        log.info("DELETE /users/{} response: success", id);
    }

    /**
     * Adds a user as a friend to another user.
     *
     * @param id       the ID of the user adding a friend
     * @param friendId the ID of the user to be added as a friend
     */
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("PUT /users/{}/friends/{} request", id, friendId);
        userService.addFriend(id, friendId);
        log.info("PUT /users/{}/friends/{} response: success", id, friendId);
    }

    /**
     * Removes a friend from a user's friend list.
     *
     * @param id       the ID of the user removing a friend
     * @param friendId the ID of the friend to remove
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("DELETE /users/{}/friends/{} request", id, friendId);
        userService.deleteFriend(id, friendId);
        log.info("DELETE /users/{}/friends/{} response: success", id, friendId);
    }

    /**
     * Retrieves the list of friends for a specific user.
     *
     * @param id the ID of the user
     * @return a collection of the user's friends
     */
    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable long id) {
        log.info("GET /users/{}/friends request", id);
        Collection<User> userFriends = userService.getFriends(id);
        log.info("GET /users/{}/friends response: {}", id, userFriends);
        return userFriends;
    }

    /**
     * Retrieves the list of mutual friends between two users.
     *
     * @param id      the ID of the first user
     * @param otherId the ID of the second user
     * @return a collection of users who are friends with both specified users
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info("GET /users/{}/friends/common/{} request", id, otherId);
        Collection<User> commonFriends = userService.getCommonFriends(id, otherId);
        log.info("GET /users/{}/friends/common/{} response: {}", id, otherId, commonFriends);
        return commonFriends;
    }

    /**
     * Retrieves film recommendations for a user.
     * <p>
     * Recommendations are based on the likes of users with similar tastes.
     * </p>
     *
     * @param userId the ID of the user to get recommendations for
     * @return a collection of recommended {@link Film} objects
     */
    @GetMapping("/{userId}/recommendations")
    public Collection<Film> getFilmRecommendations(@PathVariable long userId) {
        log.info("GET /users/{}/recommendations request", userId);
        Collection<Film> recommendedFilms = userService.getFilmRecommendations(userId);
        log.info("GET /users/{}/recommendations response: {}", userId, recommendedFilms);
        return recommendedFilms;
    }

    /**
     * Retrieves the activity feed (events) for a specific user.
     * <p>
     * The feed contains actions performed by the user, such as likes, friend additions, etc.
     * </p>
     *
     * @param userId the ID of the user
     * @return a collection of {@link Event} objects associated with the user
     */
    @GetMapping("/{userId}/feed")
    public Collection<Event> getUserFeed(@PathVariable Long userId) {
        log.info("GET /users/{}/feed request", userId);
        Collection<Event> events = userService.getFeed(userId);
        log.info("GET /users/{}/feed response: {}", userId, events);
        return events;
    }
}