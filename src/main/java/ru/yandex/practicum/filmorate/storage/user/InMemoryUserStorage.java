package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Long userId = 0L;

    private final HashMap<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setUserId(generateId());
        users.put(user.getUserId(), user);

        return user;
    }

    @Override
    public User changeUser(User user) {
        if (!users.containsKey(user.getUserId())) {
            throw new ValidationException("Такого целочисленного идентификатор нет в списке");
        }

        if (user.getLogin().isEmpty()) {
            user.setName(user.getLogin());
        }

        users.put(user.getUserId(), user);

        return user;
    }

    @Override
    public Collection<User> findMutualFriends(Long userId, Long friendId) {
        User user = users.get(userId);
        User friend = users.get(friendId);

        Set<Long> allFriends = new HashSet<>();
        allFriends.addAll(user.getFriends());
        allFriends.addAll(friend.getFriends());

        List<User> mutualFriends = new ArrayList<>();

        for (Long numberFriend : allFriends) {
            if (users.containsKey(numberFriend)) {
                mutualFriends.add(users.get(numberFriend));
            }
        }
        return mutualFriends;
    }

    @Override
    public Collection<User> getFriends(Long userId) {
        Set<Long> idFriends = users.get(userId).getFriends();
        List<User> friends = new ArrayList<>();
        for (Long numberFriend : idFriends) {
            if (users.containsKey(numberFriend)) {
                friends.add(users.get(numberFriend));
            }
        }
        return friends;
    }

    @Override
    public User addToFriends(Long userId, Long friendId) {
        User user = users.get(userId);
        User friend = users.get(friendId);
        if (user != null && friend != null) {
            user.addFriend(friend);
            friend.addFriend(user);
        }
        return user;
    }

    @Override
    public User removeFromFriends(Long userId, Long friendId) {
        User user = users.get(userId);
        User friend = users.get(friendId);
        if (user != null && friend != null) {
            user.removeFriend(friend);
            friend.removeFriend(user);
        }
        return user;
    }

    @Override
    public HashMap<Long, User> getUsers() {
        return users;
    }

    private Long generateId() {
        return ++userId;
    }
}
