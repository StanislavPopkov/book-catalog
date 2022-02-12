package ru.book.catalog.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.book.catalog.model.Author;
import ru.book.catalog.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        long authorId = resultSet.getLong("author_id");
        String name = resultSet.getString("name");
        String lastName = resultSet.getString("last_name");
        Genre genre = Genre.getGenre(resultSet.getLong("genre_id"));

        return new Author(authorId, name, lastName, null, genre);
    }
}
