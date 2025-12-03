package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.feed.FeedRepository;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import java.util.Collection;

/**
 * Implementation of the {@link UserService}.
 * <p>
 * This class handles user-related business logic.
 * Key responsibilities include:
 * <ul>
 * <li>Managing user profiles (Create, Update, Get).</li>
 * <li>Ensuring business rules (e.g., defaulting name to login if empty).</li>
 * <li>Managing social connections (Friends) and recording these events to the Feed.</li>
 * <li>Generating recommendations based on user activity.</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final FeedRepository feedRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getById(long userId) {
        return userRepository.getById(userId)
                .orElseThrow(() -> {
                    log.warn("GET USER: User with ID {} not found", userId);
                    return new NotFoundException("Пользователя с id=" + userId + " не существует");
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(User user) {
        checkAndInitializeUserName(user);
        return userRepository.create(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User update(User user) {
        checkAndInitializeUserName(user);

        return userRepository.update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long userId) {
        userRepository.delete(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFriend(long userId, long friendId) {
        checkUserExistence(userId, "ADD-FRIEND");
        checkUserExistence(friendId, "ADD-FRIEND-TARGET");

        userRepository.addFriend(userId, friendId);

        feedRepository.saveEvent(userId, Operation.ADD, EventType.FRIEND, friendId);
        log.info("User {} added friend {}", userId, friendId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFriend(long userId, long friendId) {
        checkUserExistence(userId, "REMOVE-FRIEND");
        checkUserExistence(friendId, "REMOVE-FRIEND-TARGET");

        userRepository.deleteFriend(userId, friendId);

        feedRepository.saveEvent(userId, Operation.REMOVE, EventType.FRIEND, friendId);
        log.info("User {} removed friend {}", userId, friendId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<User> getFriends(long userId) {
        checkUserExistence(userId, "GET-FRIENDS");
        return userRepository.getFriends(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<User> getCommonFriends(long userId, long otherId) {
        checkUserExistence(userId, "GET-COMMON-FRIENDS");
        checkUserExistence(otherId, "GET-COMMON-FRIENDS");
        return userRepository.getCommonFriends(userId, otherId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Film> getFilmRecommendations(long userId) {
        checkUserExistence(userId, "GET-RECOMMENDATIONS");
        return filmRepository.getFilmRecommendations(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Event> getFeed(long userId) {
        checkUserExistence(userId, "GET-FEED");
        return userRepository.getFeed(userId);
    }

    /**
     * Checks if the user's name is empty. If so, sets the name to the login value.
     */
    private void checkAndInitializeUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.debug("User name is empty, using login '{}' as name", user.getLogin());
            user.setName(user.getLogin());
        }
    }

    /**
     * Validates that a user exists in the database.
     *
     * @param userId the ID to check
     * @param operation the operation name for logging purposes
     * @throws NotFoundException if the user does not exist
     */
    private void checkUserExistence(Long userId, String operation) {
        userRepository.getById(userId)
                .orElseThrow(() -> {
                    log.warn("{}: User with ID {} not found", operation, userId);
                    return new NotFoundException("Пользователя с id=" + userId + " не существует");
                });
    }
}