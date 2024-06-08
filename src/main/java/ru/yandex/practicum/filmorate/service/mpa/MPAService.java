package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;

public interface MPAService {
    Collection<MPA> getAll();

    MPA getById(long mpaId);
}
