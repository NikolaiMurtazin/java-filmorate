package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User changeUser(User user) {
      return userStorage.changeUser(user);
    }

    public Collection<User> findMutualFriends(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);

        Set<Long> allFriends = user.getFriends();
        allFriends.retainAll(friend.getFriends());

        List<User> mutualFriends = new ArrayList<>();

        for (Long numberFriend : allFriends) {
            mutualFriends.add(userStorage.getUser(numberFriend));
        }
        return mutualFriends;
    }

    public Collection<User> getFriends(Long userId) {
        Set<Long> idFriends = userStorage.getUser(userId).getFriends();
        List<User> friends = new ArrayList<>();
        for (Long numberFriend : idFriends) {
            friends.add(userStorage.getUser(numberFriend));
        }
        return friends;
    }

    public User addToFriends(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.addFriend(friend);
        friend.addFriend(user);
        return user;
    }

    public User removeFromFriends(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.removeFriend(friend);
        friend.removeFriend(user);
        return user;
    }
}
