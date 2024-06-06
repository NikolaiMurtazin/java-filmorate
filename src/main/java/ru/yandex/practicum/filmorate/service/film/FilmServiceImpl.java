package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    public Collection<Film> getAll() {
        return filmRepository.getAll();
    }

    public Film getById(long filmId) {
        return filmRepository.getById(filmId)
                .orElseThrow(() -> new NotFoundException("Film with id " + filmId + " not found"));
    }

    public Film create(Film film) {
        return filmRepository.create(film);
    }

    public Film update(Film film) {
        getById(film.getId());
        return filmRepository.update(film);
    }

    public Collection<Film> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count);
    }
}