package ru.yandex.practicum.filmorate.repository.user;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.SaveDataException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.BaseJdbcRepository;
import ru.yandex.practicum.filmorate.repository.feed.EventMapper;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * JDBC implementation of the {@link UserRepository} interface.
 * <p>
 * Manages User entities in the database, including their profile data,
 * friendship connections, and activity feed retrieval.
 * </p>
 */
@Repository
@Primary
public class JdbcUserRepository extends BaseJdbcRepository<User> implements UserRepository {
    private final RowMapper<Event> eventMapper;

    public JdbcUserRepository(NamedParameterJdbcOperations jdbc,
                              RowMapper<User> userMapper,
                              RowMapper<Event> eventMapper) {
        super(jdbc, userMapper);
        this.eventMapper = eventMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(User user) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlQuery = """
                INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY)
                VALUES (:email, :login, :name, :birthday);
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
                .addValue("birthday", user.getBirthday());

        jdbc.update(sqlQuery, params, keyHolder);

        Long id = keyHolder.getKeyAs(Long.class);

        if (id != null) {
            user.setId(id);
        } else {
            throw new SaveDataException("Failed to save user data: " + user);
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User update(User user) {
        String sqlQuery = """
                UPDATE USERS
                SET EMAIL = :email,
                    LOGIN = :login,
                    NAME = :name,
                    BIRTHDAY = :birthday
                WHERE USER_ID = :userId;
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", user.getId())
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
                .addValue("birthday", user.getBirthday());

        int rowsUpdated = jdbc.update(sqlQuery, params);

        if (rowsUpdated == 0) {
            throw new NotFoundException("User with ID " + user.getId() + " not found");
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<User> getAll() {
        String sqlQuery = "SELECT * FROM USERS;";
        return jdbc.query(sqlQuery, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> getById(Long id) {
        try {
            String sqlQuery = """
                    SELECT USER_ID, EMAIL, LOGIN, NAME, BIRTHDAY
                    FROM USERS
                    WHERE USER_ID = :userId;
                    """;
            User user = jdbc.queryForObject(sqlQuery, Map.of("userId", id), mapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long userId) {
        String deleteUserQuery = "DELETE FROM USERS WHERE USER_ID = :userId;";
        jdbc.update(deleteUserQuery, Map.of("userId", userId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFriend(long userId, long friendId) {
        String sqlQuery = """
                MERGE INTO FRIENDSHIP (USER_ID, FRIEND_ID)
                KEY (USER_ID, FRIEND_ID)
                VALUES (:userId, :friendId);
                """;
        jdbc.update(sqlQuery, Map.of("userId", userId, "friendId", friendId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFriend(long userId, long friendId) {
        String removeFriendship = """
                DELETE FROM FRIENDSHIP
                WHERE USER_ID = :userId
                AND FRIEND_ID = :friendId;
                """;
        jdbc.update(removeFriendship, Map.of("userId", userId, "friendId", friendId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<User> getFriends(long userId) {
        String sqlQuery = """
                SELECT u.*
                FROM USERS u
                JOIN FRIENDSHIP f ON u.USER_ID = f.FRIEND_ID
                WHERE f.USER_ID = :userId;
                """;
        return jdbc.query(sqlQuery, Map.of("userId", userId), mapper);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Optimized to perform the intersection in the database using JOINs,
     * rather than fetching two lists and intersecting them in Java memory.
     * </p>
     */
    @Override
    public Collection<User> getCommonFriends(long userId, long otherId) {
        String sqlQuery = """
                SELECT u.*
                FROM USERS u
                JOIN FRIENDSHIP f1 ON u.USER_ID = f1.FRIEND_ID
                JOIN FRIENDSHIP f2 ON u.USER_ID = f2.FRIEND_ID
                WHERE f1.USER_ID = :userId AND f2.USER_ID = :otherId;
                """;

        return jdbc.query(sqlQuery, Map.of("userId", userId, "otherId", otherId), mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Event> getFeed(long userId) {
        String sqlQuery = """
                SELECT *
                FROM USER_EVENTS
                WHERE USER_ID = :userId
                ORDER BY TIMESTAMP ASC; 
                """;

        return jdbc.query(sqlQuery, Map.of("userId", userId), eventMapper);
    }
}