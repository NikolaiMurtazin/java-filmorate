package ru.yandex.practicum.filmorate.repository.mpa;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;
import java.util.Optional;

public interface MPARepository {
    Collection<MPA> getAll();

    Optional<MPA> getById(long mpaId);
}
