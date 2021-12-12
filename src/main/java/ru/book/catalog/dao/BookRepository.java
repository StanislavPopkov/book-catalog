package ru.book.catalog.dao;

import ru.book.catalog.model.Author;
import ru.book.catalog.model.Book;

import java.util.List;


public interface BookRepository {

    Book save(Book book);

    Book getById(long id);

    Book getByName(String bookName);

    List<Book> getAll();

    boolean delete(long id);
}
