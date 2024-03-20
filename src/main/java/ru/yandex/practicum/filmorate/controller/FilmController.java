package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getFilms() {
        return filmService.getFilmStorage().getFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable int filmId) {
        return filmService.getFilmStorage().getFilm(filmId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10", required = false) String count) {
        if (Integer.parseInt(count) < 0) {
            throw new ValidationException("Параметр count имеет отрицательное значение.");
        }

        return filmService.getPopularFilms(Integer.parseInt(count));
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        filmService.getFilmStorage().addFilm(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmService.getFilmStorage().updateFilm(film);
        return film;
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film setLike(@PathVariable int filmId, @PathVariable int userId) {
        return filmService.setLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film deleteLike(@PathVariable int filmId, @PathVariable int userId) {
        Film film = filmService.getFilmStorage().getFilm(filmId);


        return filmService.deleteLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}")
    public void deleteFilm(@PathVariable int filmId) {
        filmService.getFilmStorage().deleteFilm(filmId);
    }
}