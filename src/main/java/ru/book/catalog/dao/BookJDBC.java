package ru.book.catalog.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.book.catalog.model.Author;
import ru.book.catalog.model.Book;
import ru.book.catalog.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
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
            namedParameterJdbcTemplate.update("UPDATE books SET book_name = :book_name, genre_id = :genre_id, author_id = :author_id where book_id = :book_id", params);
        }
        return book;

    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.queryForObject(
                "select b.book_id, b.book_name, b.genre_id, a.author_id, a.name, a.last_name from books as b inner join " +
                        "author as a on b.author_id = a.author_id where b.book_id = :id", params, new BookMapper());
    }

    @Override
    public Book getByName(String bookName) {
        Map<String, Object> params = Collections.singletonMap("bookName", bookName);
        return namedParameterJdbcTemplate.queryForObject(
                "select  b.book_id, b.book_name, b.genre_id, a.author_id, a.name, a.last_name from books as b inner join " +
                        "authors as a on b.author_id = a.author_id where b.book_name = :bookName", params, new BookMapper());
    }


    @Override
     public List<Book> getAll() {
       //return jdbc.query("select * from persons", new PersonMapper());
         return null;
    }

    @Override
    public boolean delete(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.update("delete from books where book_id =:id", params) != 0 ;
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long bookId = resultSet.getLong("book_id");
            String bookName = resultSet.getString("book_name");
            long authorId = resultSet.getLong("author_id");
            String name = resultSet.getString("name");
            String lastName = resultSet.getString("last_name");
            Genre genre = Genre.getGenre(resultSet.getLong("genre_id"));

            Author author = new Author(authorId, name, lastName, null, genre);
            Book book = new Book(bookId, bookName, author, genre);
            author.setBook(book);
            return book;
        }
    }
}
