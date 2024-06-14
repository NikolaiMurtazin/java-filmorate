package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;
import ru.yandex.practicum.filmorate.repository.mpa.MPARepository;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final MPARepository mpaRepository;
    private final GenreRepository genreRepository;

    public Collection<Film> getAll() {
        return filmRepository.getAll();
    }

    public Film getById(long filmId) {
        return filmRepository.getById(filmId)
                .orElseThrow(() -> new NotFoundException("Film with id " + filmId + " not found"));
    }

    public Film create(Film film) {
        checkGenresAndMPA(film);
        return filmRepository.create(film);
    }

    public Film update(Film film) {
        getById(film.getId());
        checkGenresAndMPA(film);
        return filmRepository.update(film);
    }

    public Collection<Film> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count);
    }

    private void checkGenresAndMPA(Film film) {
        int mpaId = film.getMpa().getId();
        Set<Genre> genres = film.getGenres();
        for (Genre genre : genres) {
            int genreId = genre.getId();
            genreRepository.getById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Genre with id " + genreId + " not found"));
        }
        mpaRepository.getById(mpaId)
                .orElseThrow(() -> new IllegalArgumentException("MPA with id " + mpaId + " not found"));
    }
}