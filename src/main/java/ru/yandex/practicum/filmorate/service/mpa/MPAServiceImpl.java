package ru.yandex.practicum.filmorate.service.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.repository.mpa.MPARepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MPAServiceImpl implements MPAService {
    private final MPARepository mpaRepository;

    public Collection<MPA> getAll() {
        return mpaRepository.getAll();
    }

    public MPA getById(long mpaId) {
        return mpaRepository.getById(mpaId)
                .orElseThrow(() -> new NotFoundException("MPA with id " + mpaId + " not found"));
    }
}
