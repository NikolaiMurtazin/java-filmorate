package ru.yandex.practicum.filmorate.service.friendship;

public interface FriendshipService {
    void add(long userId, long friendId);

    void delete(long userId, long friendId);
}
