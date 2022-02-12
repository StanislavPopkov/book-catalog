package ru.book.catalog.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.book.catalog.model.Author;
import ru.book.catalog.model.Book;
import ru.book.catalog.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

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
