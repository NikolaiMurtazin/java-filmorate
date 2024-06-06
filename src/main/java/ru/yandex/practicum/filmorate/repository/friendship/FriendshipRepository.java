package ru.yandex.practicum.filmorate.repository.friendship;

import ru.yandex.practicum.filmorate.model.User;

public interface FriendshipRepository {
    void add(User user, User friend);

    void delete(User user, User friend);
}
