package ru.book.catalog.dao;

import ru.book.catalog.model.Author;

import java.util.List;

public interface AuthorRepository {

    Author save(Author book);

    Author getById(long id);

    Author getByName(String name, String lastname);

    List<Author> getAll();

    boolean delete(long id);
}
