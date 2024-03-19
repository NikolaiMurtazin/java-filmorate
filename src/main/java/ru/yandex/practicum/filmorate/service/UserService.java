package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User change(User user) {
      return userStorage.change(user);
    }

    public Collection<User> findMutualFriends(Long userId, Long friendId) {
        User user = userStorage.getUsers().get(userId);
        User friend = userStorage.getUsers().get(friendId);

        Set<Long> allFriends = new HashSet<>();
        allFriends.addAll(user.getFriends());
        allFriends.addAll(friend.getFriends());

        List<User> mutualFriends = new ArrayList<>();

        for (Long numberFriend : allFriends) {
            if (userStorage.getUsers().containsKey(numberFriend)) {
                mutualFriends.add(userStorage.getUsers().get(numberFriend));
            }
        }

        return mutualFriends;
    }

    public Collection<User> getFriends(Long userId) {
        Set<Long> idFriends = userStorage.getUsers().get(userId).getFriends();
        List<User> friends = new ArrayList<>();
        for (Long numberFriend : idFriends) {
            if (userStorage.getUsers().containsKey(numberFriend)) {
                friends.add(userStorage.getUsers().get(numberFriend));
            }
        }
        return friends;
    }

    public User addToFriends(Long userId, Long friendId) {
        User user = userStorage.getUsers().get(userId);
        User friend = userStorage.getUsers().get(friendId);
        if (user != null && friend != null) {
            user.addFriend(friend);
            friend.addFriend(user);
        }
        return user;
    }

    public User removeFromFriends(Long userId, Long friendId) {
        User user = userStorage.getUsers().get(userId);
        User friend = userStorage.getUsers().get(friendId);
        if (user != null && friend != null) {
            user.removeFriend(friend);
            friend.removeFriend(user);
        }
        return user;
    }
}
