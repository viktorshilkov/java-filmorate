package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Пришел GET запрос /users");
        List<User> users = userService.getUsers();
        log.info("Отправлен ответ GET /users с телом: {}", users);
        return users;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int id) {
        log.info("Пришел GET запрос /users/{userId}");
        User user = userService.getUser(id);
        log.info("Отправлен ответ GET /users/{userId} с телом: {}", user);
        return user;
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Пришел GET запрос /users/{id}/friends");
        List<User> friends = userService.getFriends(id);
        log.info("Отправлен ответ GET /users/{id}/friends с телом: {}", friends);
        return friends;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Пришел GET запрос /users/{id}/friends/common/{otherId}");
        List<User> mutualFriends = userService.findMutualFriends(id, otherId);
        log.info("Отправлен ответ GET /users/{id}/friends/common/{otherId} с телом: {}", mutualFriends);
        return mutualFriends;
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Пришел POST запрос /users с телом: {}", user);
        User newUser = userService.addUser(user);
        log.info("Отправлен ответ POST /users с телом: {}", user);
        return newUser;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Пришел PUT запрос /users с телом: {}", user);
        User newUser = userService.updateUser(user);
        log.info("Отправлен ответ PUT /users с телом: {}", user);
        return newUser;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Пришел POST запрос /users/{id}/friends/{friendId}");
        User user = userService.addFriend(id, friendId);
        log.info("Отправлен ответ POST /users/{id}/friends/{friendId} с телом: {}", user);
        return user;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable int userId) {
        log.info("Пришел DELETE запрос /users/{userId}");
        userService.deleteUser(userId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Пришел DELETE запрос /users/{id}/friends/{friendId}");
        userService.deleteFriend(id, friendId);
    }
}