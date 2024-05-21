-- Создание таблицы User
CREATE TABLE IF NOT EXISTS Users
(
    user_id  INT PRIMARY KEY AUTO_INCREMENT,
    login    VARCHAR(50)  NOT NULL,
    name     VARCHAR(100),
    email    VARCHAR(100) NOT NULL,
    birthday DATE
);

-- Создание таблицы Friends
CREATE TABLE IF NOT EXISTS Friends
(
    user1_id INT,
    user2_id INT,
    status   BOOLEAN,
    PRIMARY KEY (user1_id, user2_id),
    FOREIGN KEY (user1_id) REFERENCES Users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (user2_id) REFERENCES Users (user_id) ON DELETE CASCADE
);

-- Создание таблицы MPA
CREATE TABLE IF NOT EXISTS MPA
(
    MPA_id INT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(50) NOT NULL
);

-- Создание таблицы Genre
CREATE TABLE IF NOT EXISTS Genre
(
    genre_id INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(50) NOT NULL
);

-- Создание таблицы Film
CREATE TABLE IF NOT EXISTS Film
(
    film_id     INT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    releaseDate DATE,
    duration    INT,
    MPA_id      INT,
    genre_id    INT,
    FOREIGN KEY (MPA_id) REFERENCES MPA (MPA_id),
    FOREIGN KEY (genre_id) REFERENCES Genre (genre_id)
);

-- Создание таблицы film_genres
CREATE TABLE IF NOT EXISTS film_genres
(
    film_id  INT,
    genre_id INT,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES Film (film_id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES Genre (genre_id) ON DELETE CASCADE
);

-- Создание таблицы like_film
CREATE TABLE IF NOT EXISTS like_film
(
    film_id INT,
    user_id INT,
    PRIMARY KEY (film_id, user_id),
    FOREIGN KEY (film_id) REFERENCES Film (film_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE
);