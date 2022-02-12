DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS book;

CREATE SEQUENCE GLOBAL_SEQ;

CREATE TABLE genre(genre_id BIGINT PRIMARY KEY, genre VARCHAR(255) NOT NULL);
CREATE UNIQUE INDEX genre_index ON genre (genre);

CREATE TABLE authors(author_id BIGINT default GLOBAL_SEQ.nextval PRIMARY KEY, name VARCHAR(255) NOT NULL, last_name VARCHAR(255) NOT NULL, genre_id BIGINT,
FOREIGN KEY (genre_id) REFERENCES genre (genre_id));
CREATE UNIQUE INDEX name_lastName_index ON authors (name, last_name);

CREATE TABLE books(book_id BIGINT default GLOBAL_SEQ.nextval PRIMARY KEY, book_name VARCHAR(255), genre_id BIGINT, author_id BIGINT,
FOREIGN KEY (genre_id) REFERENCES genre (genre_id),
FOREIGN KEY (author_id) REFERENCES authors (author_id));
CREATE UNIQUE INDEX book_genre_author_index ON books (book_name, genre_id, author_id);