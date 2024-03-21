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
        log.info("Пришел GET запрос /films ");
        List<Film> filmsList = filmService.getFilms();
        log.info("Отправлен ответ GET /films с телом: {}", filmsList);
        return filmsList;
    }

    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable int filmId) {
        log.info("Пришел GET запрос /films/{filmId}");
        Film film = filmService.getFilm(filmId);
        log.info("Отправлен ответ GET /films/{filmId} с телом: {}", film);
        return film;
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10", required = false) String count) {
        log.info("Пришел GET запрос /films/popular");
        if (Integer.parseInt(count) < 0) {
            throw new ValidationException("Параметр count имеет отрицательное значение.");
        }

        List<Film> popularFilms = filmService.getPopularFilms(Integer.parseInt(count));
        log.info("Отправлен ответ GET /films/popular с телом: {}", popularFilms);
        return popularFilms;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Пришел POST запрос /films с телом: {}", film);
        Film newFilm = filmService.addFilm(film);
        log.info("Отправлен ответ POST /films с телом: {}", film);
        return newFilm;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Пришел PUT запрос /films с телом: {}", film);
        Film newFilm = filmService.updateFilm(film);
        log.info("Отправлен ответ PUT /films с телом: {}", film);
        return newFilm;
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film setLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("Пришел PUT запрос /films/{filmId}/like/{userId}");
        Film film = filmService.setLike(filmId, userId);
        log.info("Отправлен ответ PUT /films/{filmId}/like/{userId} с телом: {}", film);
        return film;
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film deleteLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("Пришел DELETE запрос /films/{filmId}/like/{userId}");
        Film film = filmService.deleteLike(filmId, userId);
        log.info("Отправлен ответ DELETE /films/{filmId}/like/{userId} с телом: {}", film);
        return film;
    }

    @DeleteMapping("/{filmId}")
    public void deleteFilm(@PathVariable int filmId) {
        log.info("Пришел DELETE запрос /films/{filmId}");
        filmService.deleteFilm(filmId);
    }
}