package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film setLike(int filmId, int userId) {
        final Film film = filmStorage.getFilm(filmId);
        film.setLike(userId);
        return film;
    }

    public Film deleteLike(int filmId, int userId) {
        final Film film = filmStorage.getFilm(filmId);
        film.deleteLike(userId);
        return film;
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted(Comparator.comparingInt(film -> ((Film) film).getLikes().size()).reversed())
                .limit(count).collect(Collectors.toList());
    }

    public Film getFilm(int id) {
        return filmStorage.getFilm(id);
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public void deleteFilm(int filmId) {
        filmStorage.deleteFilm(filmId);
    }

    public Film updateFilm(Film film) {
        Film newFilm = checkFilm(film);
        return filmStorage.updateFilm(newFilm);
    }

    public Film addFilm(Film film) {
        Film newFilm = checkFilm(film);
        return filmStorage.addFilm(newFilm);
    }

    private Film checkFilm(Film film) {
        final LocalDate startDate = LocalDate.of(1895, 12, 27);
        if (film.getReleaseDate().isBefore(startDate)) {
            throw new ValidationException("Date must be after 1895.12.27");
        }
        return film;
    }
}