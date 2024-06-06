package ru.yandex.practicum.filmorate.service.filmLike;

public interface FilmLikeService {
    void addLikeToFilm(long filmId, long userId);

    void deleteLikeFromFilm(long filmId, long userId);
}
