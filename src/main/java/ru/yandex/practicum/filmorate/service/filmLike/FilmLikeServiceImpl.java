package ru.yandex.practicum.filmorate.service.filmLike;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.filmLike.FilmLikeRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class FilmLikeServiceImpl implements FilmLikeService {
    private final FilmLikeRepository filmLikeRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    public void addLikeToFilm(long filmId, long userId) {
        Film film = filmRepository.getById(filmId)
                .orElseThrow(() -> new NotFoundException("Film with id " + filmId + " not found"));
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        filmLikeRepository.addLike(film, user);
    }

    public void deleteLikeFromFilm(long filmId, long userId) {
        Film film = filmRepository.getById(filmId)
                .orElseThrow(() -> new NotFoundException("Film with id " + filmId + " not found"));
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        filmLikeRepository.deleteLike(film, user);
    }
}
