package ru.yandex.practicum.filmorate.repository.filmLike;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

@Repository
@RequiredArgsConstructor
public class JdbcFilmLikeRepository implements FilmLikeRepository {
    private final NamedParameterJdbcOperations jdbc;

    private static final String SQL_ADD_LIKE = """
        INSERT INTO FILM_LIKES (FILM_ID, USER_ID)
        VALUES (:filmId, :userId)
        """;

    private static final String SQL_DELETE_LIKE = """
        DELETE FROM FILM_LIKES
        WHERE FILM_ID = :filmId AND USER_ID = :userId
        """;

    @Override
    public void addLike(Film film, User user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("filmId", film.getId())
                .addValue("userId", user.getId());
        jdbc.update(SQL_ADD_LIKE, params);
    }

    @Override
    public void deleteLike(Film film, User user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("filmId", film.getId())
                .addValue("userId", user.getId());
        jdbc.update(SQL_DELETE_LIKE, params);
    }
}
