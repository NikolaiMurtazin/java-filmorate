package ru.yandex.practicum.filmorate.service.friendship;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.friendship.FriendshipRepository;
import ru.yandex.practicum.filmorate.service.user.UserService;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserService userService;

    public void add(long userId, long friendId) {
        User user = userService.getById(userId);
        User friend = userService.getById(friendId);
        friendshipRepository.add(user, friend);
    }

    public void delete(long userId, long friendId) {
        User user = userService.getById(userId);
        User friend = userService.getById(friendId);
        friendshipRepository.delete(user, friend);
    }
}