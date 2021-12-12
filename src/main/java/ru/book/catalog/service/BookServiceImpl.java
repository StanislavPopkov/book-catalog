package ru.book.catalog.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.book.catalog.dao.BookRepository;
import ru.book.catalog.model.Book;

import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        Objects.requireNonNull(book);
        return bookRepository.save(book);
    }

    @Override
    public Book getById(long id) {
        try {
            return bookRepository.getById(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getByName(String bookName) {
        Objects.requireNonNull(bookName);
        try {
            return bookRepository.getByName(bookName);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public boolean delete(long id) {
        return bookRepository.delete(id);
    }
}
