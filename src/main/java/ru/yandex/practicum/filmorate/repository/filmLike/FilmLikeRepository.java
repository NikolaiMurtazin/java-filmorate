package ru.yandex.practicum.filmorate.repository.filmLike;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

public interface FilmLikeRepository {
    void addLike(Film film, User user);

    void deleteLike(Film film, User user);
}
