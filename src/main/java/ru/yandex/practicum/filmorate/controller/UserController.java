package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int generatedId = 0;

    @GetMapping("/users")
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public User addUser(@Valid @RequestBody User user) {
        log.info("Пришел POST запрос /users с телом: {}", user);
        User newUser = checkUser(user);
        newUser.setId(++generatedId);
        users.put(generatedId, newUser);
        log.info("Отправлен ответ POST /users с телом: {}", user);
        return newUser;
    }

    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Пришел PUT запрос /users с телом: {}", user);
        if (users.get(user.getId()) == null) {
            throw new ValidationException("User with id=" + user.getId() + " not found");
        }

        users.put(user.getId(), user);
        log.info("Отправлен ответ PUT /users с телом: {}", user);
        return user;
    }

    private User checkUser(User user) {
        final LocalDate endTime = LocalDate.now();
        if (user.getBirthday().isAfter(endTime)) {
            throw new ValidationException("Birthday in future");
        } else if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        return user;
    }
}