package ru.yandex.practicum.filmorate.service.filmLike;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.filmLike.FilmLikeRepository;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.user.UserService;

@Service
@RequiredArgsConstructor
public class FilmLikeServiceImpl implements FilmLikeService {
    private final FilmLikeRepository filmLikeRepository;
    private final FilmService filmService;
    private final UserService userService;

    public void addLikeToFilm(long filmId, long userId) {
        Film film = filmService.getById(filmId);
        User user = userService.getById(userId);
        filmLikeRepository.addLike(film, user);
    }

    public void deleteLikeFromFilm(long filmId, long userId) {
        Film film = filmService.getById(filmId);
        User user = userService.getById(userId);
        filmLikeRepository.deleteLike(film, user);
    }
}
