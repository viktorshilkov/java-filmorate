package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film getFilm(int id);
    List<Film> getFilms();
    Film addFilm(Film film);
    void deleteFilm(int id);
    Film updateFilm(Film film);
}