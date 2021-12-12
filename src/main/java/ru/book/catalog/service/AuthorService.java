package ru.book.catalog.service;

import ru.book.catalog.model.Author;

import java.util.List;

public interface AuthorService {

    Author save(Author book);

    Author getById(long id);

    Author getByName(String name, String lastname);

    List<Author> getAll();

    boolean delete(long id);
}
