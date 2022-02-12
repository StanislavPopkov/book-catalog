package ru.book.catalog.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.book.catalog.dao.mapper.BookMapper;
import ru.book.catalog.model.Book;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookJDBC implements BookRepository{
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;


    public BookJDBC(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate()).withTableName("books")
                .usingGeneratedKeyColumns("book_id");;
    }

    @Override
    public Book save(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("book_id", book.getBookId());
        params.put("book_name", book.getBookName());
        params.put("genre_id", book.getGenre().getId());
        params.put("author_id", book.getAuthor().getAuthorId());
        if (book.isNew()) {
            //BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(book);
            Number number = simpleJdbcInsert.executeAndReturnKey(params);
            book.setBookId(number.longValue());
        } else {
            namedParameterJdbcTemplate.update("UPDATE books SET book_name = :book_name, genre_id = :genre_id, author_id = :author_id WHERE book_id = :book_id", params);
        }
        return book;

    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.queryForObject(
                "SELECT b.book_id, b.book_name, b.genre_id, a.author_id, a.name, a.last_name FROM books as b INNER JOIN " +
                        "author as a ON b.author_id = a.author_id WHERE b.book_id = :id", params, new BookMapper());
    }

    @Override
    public Book getByName(String bookName) {
        Map<String, Object> params = Collections.singletonMap("bookName", bookName);
        return namedParameterJdbcTemplate.queryForObject(
                "SELECT  b.book_id, b.book_name, b.genre_id, a.author_id, a.name, a.last_name FROM books as b INNER JOIN " +
                        "authors as a ON b.author_id = a.author_id WHERE b.book_name = :bookName", params, new BookMapper());
    }


    @Override
     public List<Book> getAll() {
        return namedParameterJdbcTemplate.query("SELECT b.book_id, b.book_name, b.genre_id, a.author_id, a.name, a.last_name " +
                "FROM books as b INNER JOIN authors as a ON b.author_id = a.author_id", new BookMapper());
    }

    @Override
    public boolean delete(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.update("DELETE FROM books WHERE book_id =:id", params) != 0 ;
    }
}
