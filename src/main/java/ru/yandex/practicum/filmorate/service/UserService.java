package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public User addFriend(int userId, int otherId) {
        log.info("Пришел POST запрос /users/{id}/friends/{friendId}");
        final User user = userStorage.getUser(userId);
        final User otherUser = userStorage.getUser(otherId);
        user.addFriend(otherId);
        otherUser.addFriend(userId);
        log.info("Отправлен ответ POST /users/{id}/friends/{friendId} с телом: {}", user);
        return user;
    }

    public void deleteFriend(int userId, int friendId) {
        log.info("Пришел DELETE запрос /users/{id}/friends/{friendId}");
        final User user = userStorage.getUser(userId);
        final User friend = userStorage.getUser(friendId);
        user.deleteFriend(friendId);
        friend.deleteFriend(userId);
        log.info("Отправлен ответ DELETE /films/{id}/friends/{friendId}");
    }

    public List<User> getFriends(int userId) {
        log.info("Пришел GET запрос /users/{id}/friends");
        List<User> friends = new ArrayList<>();
        for (Integer friendId : userStorage.getUser(userId).getFriends()) {
            friends.add(userStorage.getUser(friendId));
        }

        log.info("Отправлен ответ GET /users/{id}/friends с телом: {}", friends);
        return friends;
    }

    public List<User> findMutualFriends(int userId, int otherId) {
        log.info("Пришел GET запрос /users/{id}/friends/common/{otherId}");
        List<User> mutualFriends = new ArrayList<>();
        final User user = userStorage.getUser(userId);
        final User otherUser = userStorage.getUser(otherId);
        for (Integer id : user.getFriends()) {
            for (Integer secondId : otherUser.getFriends()) {
                if (id.equals(secondId)) {
                    final User mutualFriend = userStorage.getUser(id);
                    mutualFriends.add(mutualFriend);
                }
            }
        }

        log.info("Отправлен ответ GET /users/{id}/friends/common/{otherId} с телом: {}", mutualFriends);
        return mutualFriends;
    }
}