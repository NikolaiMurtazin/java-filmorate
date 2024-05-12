package ru.yandex.practicum.filmorate.repository.user;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
public class InMemoryUserRepository implements UserRepository {
    private long generator = 0L;

    private final HashMap<Long, User> userMap = new HashMap<>();

    private final HashMap<Long, Set<Long>> userFriendIds = new HashMap<>();

    @Override
    public Collection<User> getAll() {
        return userMap.values();
    }

    @Override
    public Optional<User> get(long userId) {
        if (!userMap.containsKey(userId)) {
            throw new NotFoundException("ID not found in the List");
        }
        return Optional.of(userMap.get(userId));
    }

    @Override
    public User create(User user) {
        user.setId(generateId());
        userMap.put(user.getId(), user);

        return user;
    }

    @Override
    public User update(User user) {
        if (!userMap.containsKey(user.getId())) {
            throw new NotFoundException("ID not found in the List");
        }

        if (user.getLogin().isEmpty()) {
            user.setName(user.getLogin());
        }

        userMap.put(user.getId(), user);

        return user;
    }

    @Override
    public void addFriend(User user, User friend) {
        Set<Long> uFriendIds = userFriendIds.computeIfAbsent(user.getId(), id -> new HashSet<>());
        uFriendIds.add(friend.getId());

        Set<Long> fFriendIds = userFriendIds.computeIfAbsent(friend.getId(), id -> new HashSet<>());
        fFriendIds.add(user.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        Set<Long> uFriendIds = userFriendIds.computeIfAbsent(user.getId(), id -> new HashSet<>());
        uFriendIds.remove(friend.getId());

        Set<Long> fFriendIds = userFriendIds.computeIfAbsent(friend.getId(), id -> new HashSet<>());
        fFriendIds.remove(user.getId());
    }

    @Override
    public Collection<User> getCommonFriends(User user, User friend) {
        Set<Long> allFriends = userFriendIds.get(user.getId());
        allFriends.retainAll(userFriendIds.get(friend.getId()));

        List<User> mutualFriends = new ArrayList<>();

        for (Long numberFriend : allFriends) {
            mutualFriends.add(userMap.get(numberFriend));
        }
        return mutualFriends;
    }

    @Override
    public Collection<User> getFriends(User user) {
        if (user == null || !userMap.containsKey(user.getId())) {
            return Collections.emptyList();
        }

        Set<Long> idFriends = userFriendIds.getOrDefault(user.getId(), Collections.emptySet());

        return idFriends.stream()
                .map(userMap::get)
                .collect(Collectors.toList());
    }

    private Long generateId() {
        return ++generator;
    }
}
