package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    public User getById(long userId) {
        return userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }

    public User create(User user) {
        return userRepository.create(user);
    }

    public User update(User user) {
        getById(user.getId());
        return userRepository.update(user);
    }

    public Collection<User> getFriends(long userId) {
        User user = getById(userId);
        return userRepository.getFriends(user);
    }

    public Collection<User> getCommonFriends(long userId, long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);
        return userRepository.getCommonFriends(user, friend);
    }
}
