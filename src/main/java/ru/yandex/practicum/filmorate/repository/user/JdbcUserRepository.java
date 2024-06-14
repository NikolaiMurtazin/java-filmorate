package ru.yandex.practicum.filmorate.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    private final NamedParameterJdbcOperations jdbc;

    private static final String SQL_GET_ALL =  "SELECT * FROM USERS";

    private static final String SQL_GET_BY_ID = "SELECT * FROM USERS WHERE USER_ID = :userId";

    private static final String SQL_CREATE = """
        INSERT INTO USERS
            (LOGIN, NAME, EMAIL, BIRTHDAY)
        VALUES
            (:login, :name, :email, :birthday)
        """;

    private static final String SQL_UPDATE = """
        UPDATE USERS
        SET
            LOGIN = :login,
            NAME = :name,
            EMAIL = :email,
            BIRTHDAY = :birthday
        WHERE
            USER_ID = :userId
        """;

    private static final String SQL_GET_COMMON_FRIENDS = """
        SELECT u.*
        FROM
            USERS u
            JOIN FRIENDSHIPS f1 ON u.USER_ID = f1.FRIEND_ID
            JOIN FRIENDSHIPS f2 ON u.USER_ID = f2.FRIEND_ID
        WHERE
            f1.USER_ID = :userId
            AND f2.USER_ID = :friendId
        """;

    private static final String SQL_GET_FRIENDS = """
        SELECT u.*
        FROM
            USERS u
            JOIN FRIENDSHIPS f ON u.USER_ID = f.FRIEND_ID
        WHERE
            f.USER_ID = :userId
        """;

    @Override
    public Collection<User> getAll() {
        return jdbc.query(SQL_GET_ALL, new UserRowMapper());
    }

    @Override
    public Optional<User> getById(long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbc.query(SQL_GET_BY_ID, params, new UserRowMapper()).stream().findFirst();
    }

    @Override
    public User create(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("birthday", user.getBirthday());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(SQL_CREATE, params, keyHolder, new String[]{"USER_ID"});
        long userId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        user.setId(userId);
        return user;
    }

    @Override
    public User update(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("birthday", user.getBirthday())
                .addValue("userId", user.getId());
        jdbc.update(SQL_UPDATE, params);
        return user;
    }

    @Override
    public Collection<User> getCommonFriends(User user, User friend) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", user.getId())
                .addValue("friendId", friend.getId());
        return jdbc.query(SQL_GET_COMMON_FRIENDS, params, new UserRowMapper());
    }

    @Override
    public Collection<User> getFriends(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", user.getId());
        return jdbc.query(SQL_GET_FRIENDS, params, new UserRowMapper());
    }
}