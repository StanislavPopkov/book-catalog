package ru.book.catalog.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.book.catalog.dao.AuthorRepository;
import ru.book.catalog.model.Author;

import java.util.List;
import java.util.Objects;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(Author author) {
        Objects.requireNonNull(author);
        return authorRepository.save(author);
    }

    @Override
    public Author getById(long id) {
        try {
            return authorRepository.getById(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Author getByName(String name, String lastname) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(lastname);
        try {
            return authorRepository.getByName(name, lastname);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    @Override
    public boolean delete(long id) {
        return authorRepository.delete(id);
    }
}
