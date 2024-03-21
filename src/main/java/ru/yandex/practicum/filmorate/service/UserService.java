package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
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

    public User addFriend(int userId, int otherId) {
        final User user = userStorage.getUser(userId);
        final User otherUser = userStorage.getUser(otherId);
        user.addFriend(otherId);
        otherUser.addFriend(userId);
        return user;
    }

    public void deleteFriend(int userId, int friendId) {
        final User user = userStorage.getUser(userId);
        final User friend = userStorage.getUser(friendId);
        user.deleteFriend(friendId);
        friend.deleteFriend(userId);
        log.info("Пользователь с id=" + friendId + " удалён из друзей");
    }

    public List<User> getFriends(int userId) {
        List<User> friends = new ArrayList<>();
        for (Integer friendId : userStorage.getUser(userId).getFriends()) {
            friends.add(userStorage.getUser(friendId));
        }
        return friends;
    }

    public List<User> findMutualFriends(int userId, int otherId) {
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
        return mutualFriends;
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUser(int id) {
        return userStorage.getUser(id);
    }

    public User addUser(User user) {
        User newUser = checkUser(user);
        return userStorage.addUser(newUser);
    }

    public User updateUser(User user) {
        User newUser = checkUser(user);
        return userStorage.updateUser(newUser);
    }

    public void deleteUser(int userId) {
        userStorage.deleteUser(userId);
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