package ru.yandex.practicum.filmorate.repository.friendship;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

@Repository
@RequiredArgsConstructor
public class JdbcFriendshipRepository implements FriendshipRepository {

    private final NamedParameterJdbcOperations jdbc;

    private static final String SQL_ADD = """
        INSERT INTO FRIENDSHIPS (USER_ID, FRIEND_ID, IS_MUTUAL)
        VALUES (:userId, :friendId, :isMutual)
        """;

    private static final String SQL_DELETE = """
        DELETE FROM FRIENDSHIPS
        WHERE USER_ID = :userId AND FRIEND_ID = :friendId
        """;

    private static final String SQL_UPDATE_FRIENDSHIP_TO_MUTUAL = """
        UPDATE FRIENDSHIPS
        SET IS_MUTUAL = TRUE
        WHERE USER_ID = :userId AND FRIEND_ID = :friendId
        """;

    private static final String SQL_CHECK_REVERSE_FRIENDSHIP = """
        SELECT COUNT(*)
        FROM FRIENDSHIPS
        WHERE USER_ID = :userId AND FRIEND_ID = :friendId
        """;

    @Override
    public void add(User user, User friend) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", user.getId())
                .addValue("friendId", friend.getId())
                .addValue("isMutual", false);

        jdbc.update(SQL_ADD, namedParameters);

        if (isReverseFriendshipExists(user, friend)) {
            SqlParameterSource updateParameters1 = new MapSqlParameterSource()
                    .addValue("userId", user.getId())
                    .addValue("friendId", friend.getId());
            jdbc.update(SQL_UPDATE_FRIENDSHIP_TO_MUTUAL, updateParameters1);

            SqlParameterSource updateParameters2 = new MapSqlParameterSource()
                    .addValue("userId", friend.getId())
                    .addValue("friendId", user.getId());
            jdbc.update(SQL_UPDATE_FRIENDSHIP_TO_MUTUAL, updateParameters2);
        }
    }

    @Override
    public void delete(User user, User friend) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", user.getId())
                .addValue("friendId", friend.getId());
        jdbc.update(SQL_DELETE, namedParameters);

        if (isReverseFriendshipExists(user, friend)) {
            SqlParameterSource updateParameters = new MapSqlParameterSource()
                    .addValue("userId", friend.getId())
                    .addValue("friendId", user.getId())
                    .addValue("isMutual", false);
            jdbc.update(SQL_UPDATE_FRIENDSHIP_TO_MUTUAL, updateParameters);
        }
    }

    private boolean isReverseFriendshipExists(User user, User friend) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", user.getId())
                .addValue("friendId", friend.getId());
        Integer count = jdbc.queryForObject(SQL_CHECK_REVERSE_FRIENDSHIP, namedParameters, Integer.class);
        return count != null && count > 0;
    }
}
