package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;

    @Override
    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User get(long userId) {
        final User user = userRepository.get(userId);
        if (user == null) {
            throw new NotFoundException("User not found with " + userId);
        }
        return user;
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
        final User saved = userRepository.get(userId);
        if (saved == null) {
            throw new NotFoundException("User not found");
        }

        return userRepository.update(user);
    }

    @Override
    public void addFriend(long userId, long friendId) {
        final User user = userRepository.get(userId);
        if (user == null) {
            throw new NotFoundException("User not found with " + userId);
        }

        final User friend = userRepository.get(friendId);
        if (friend == null) {
            throw new NotFoundException("User not found with " + userId);
        }
        userRepository.addFriend(user, friend);
    }

    @Override
    public void deleteFriend(long userId, long friendId) {
        final User user = userRepository.get(userId);
        if (user == null) {
            throw new NotFoundException("User not found with " + userId);
        }

        final User friend = userRepository.get(friendId);
        if (friend == null) {
            throw new NotFoundException("User not found with " + userId);
        }
        userRepository.deleteFriend(user, friend);
    }

    @Override
    public Collection<User> getFriends(long userId) {
        final User user = userRepository.get(userId);
        if (user == null) {
            throw new NotFoundException("User not found with " + userId);
        }

        return userRepository.getFriends(user);
    }

    @Override
    public Collection<User> getCommonFriends(long userId, long friendId) {
        final User user = userRepository.get(userId);
        if (user == null) {
            throw new NotFoundException("User not found with " + userId);
        }

        final User friend = userRepository.get(friendId);
        if (friend == null) {
            throw new NotFoundException("User not found with " + userId);
        }
        return userRepository.getCommonFriends(user, friend);
    }
}
