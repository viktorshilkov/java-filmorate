package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

@SpringBootApplication
public class FilmorateApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FilmorateApplication.class, args);
		FilmStorage filmStorage = context.getBean(FilmStorage.class);

	}
}