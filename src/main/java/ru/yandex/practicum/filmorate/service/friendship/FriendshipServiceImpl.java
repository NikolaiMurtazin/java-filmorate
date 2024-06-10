package ru.yandex.practicum.filmorate.service.friendship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.friendship.FriendshipRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public void add(long userId, long friendId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        User friend = userRepository.getById(friendId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + friendId));
        friendshipRepository.add(user, friend);
    }

    public void delete(long userId, long friendId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        User friend = userRepository.getById(friendId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + friendId));
        friendshipRepository.delete(user, friend);
    }
}