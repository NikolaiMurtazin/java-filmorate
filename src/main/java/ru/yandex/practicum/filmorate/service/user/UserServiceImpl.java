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

    @Override
    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User get(long userId) {
        return userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with " + userId));
    }

    @Override
    public User save(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return userRepository.create(user);
    }

    @Override
    public User update(User user) {
        long userId = user.getId();
        final User saved = userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with " + userId));

        return userRepository.update(user);
    }

    @Override
    public void addFriend(long userId, long friendId) {
        final User user = userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with " + userId));

        final User friend = userRepository.get(friendId)
                .orElseThrow(() -> new NotFoundException("User not found with " + friendId));
        userRepository.addFriend(user, friend);
    }

    @Override
    public void deleteFriend(long userId, long friendId) {
        final User user = userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with " + userId));

        final User friend = userRepository.get(friendId)
                .orElseThrow(() -> new NotFoundException("User not found with " + friendId));
        userRepository.deleteFriend(user, friend);
    }

    @Override
    public Collection<User> getFriends(long userId) {
        final User user = userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with " + userId));

        return userRepository.getFriends(user);
    }

    @Override
    public Collection<User> getCommonFriends(long userId, long friendId) {
        final User user = userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with " + userId));

        final User friend = userRepository.get(friendId)
                .orElseThrow(() -> new NotFoundException("User not found with " + friendId));
        return userRepository.getCommonFriends(user, friend);
    }
}
