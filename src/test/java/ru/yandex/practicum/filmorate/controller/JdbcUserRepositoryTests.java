package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.user.JdbcUserRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({JdbcUserRepository.class})
class JdbcUserRepositoryTests {

    private final JdbcUserRepository userRepository;

    @Test
    void testCreateAndFindUserById() {
        User user = User.builder()
                .login("Nikolai")
                .name("Николай")
                .email("nikolai@test.com")
                .birthday(LocalDate.of(1995, 8, 1))
                .build();

        User createdUser = userRepository.create(user);

        Optional<User> userOptional = userRepository.getById(createdUser.getId());

        assertTrue(userOptional.isPresent());
        assertThat(userOptional).contains(createdUser);
    }

    @Test
    void testGetAllUsers() {
        User user1 = User.builder()
                .login("Nikolai")
                .name("Николай")
                .email("nikolai@test.com")
                .birthday(LocalDate.of(1995, 8, 1))
                .build();
        userRepository.create(user1);

        User user2 = User.builder()
                .login("Diana")
                .name("Диана")
                .email("diana@test.com")
                .birthday(LocalDate.of(1997, 11, 12))
                .build();
        userRepository.create(user2);

        Collection<User> users = userRepository.getAll();
        assertThat(users).hasSize(2);
    }

    @Test
    void testUpdateUser() {
        User user = User.builder()
                .login("Nikolai")
                .name("Николай")
                .email("nikolai@test.com")
                .birthday(LocalDate.of(1995, 8, 1))
                .build();

        User createdUser = userRepository.create(user);

            createdUser.setName("Николай 2.0");
        userRepository.update(createdUser);

        Optional<User> userOptional = userRepository.getById(createdUser.getId());
        assertTrue(userOptional.isPresent());
        assertThat(userOptional.get().getName()).isEqualTo("Николай 2.0");
    }
}