MERGE INTO MPAS (NAME)
    KEY (NAME)
    VALUES
        ('G'),
        ('PG'),
        ('PG-13'),
        ('R'),
        ('NC-17');

-- Заполнение таблицы GENRES
MERGE INTO GENRES (NAME)
    KEY (NAME)
    VALUES
        ('Комедия'),
        ('Драма'),
        ('Мультфильм'),
        ('Триллер'),
        ('Документальный'),
        ('Боевик');