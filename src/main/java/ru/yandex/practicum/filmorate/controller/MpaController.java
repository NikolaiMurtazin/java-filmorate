package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

import java.util.Collection;

/**
 * REST Controller for managing MPA (Motion Picture Association) ratings.
 * <p>
 * This controller provides endpoints to retrieve MPA rating information, which serves as
 * reference data for films.
 * </p>
 */
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
@Slf4j
public class MpaController {

    private final MpaService mpaService;

    /**
     * Retrieves all available MPA ratings.
     *
     * @return a collection of all MPA ratings
     */
    @GetMapping
    public Collection<Mpa> getAll() {
        log.info("GET /mpa request");
        Collection<Mpa> mpas = mpaService.getAll();
        log.info("GET /mpa response: {}", mpas.size());
        return mpas;
    }

    /**
     * Retrieves a specific MPA rating by its ID.
     *
     * @param mpaId the ID of the MPA rating to retrieve
     * @return the requested MPA rating object
     */
    @GetMapping("/{mpaId}")
    public Mpa getById(@PathVariable int mpaId) {
        log.info("GET /mpa/{} request", mpaId);
        Mpa mpa = mpaService.getById(mpaId);
        log.info("GET /mpa/{} response: {}", mpaId, mpa);
        return mpa;
    }
}