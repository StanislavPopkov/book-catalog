package ru.book.catalog.service;

import ru.book.catalog.model.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    Book getById(long id);

    Book getByName(String bookName);

    List<Book> getAll();

    boolean delete(long id);
}
