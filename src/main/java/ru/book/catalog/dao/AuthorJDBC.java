package ru.book.catalog.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.book.catalog.dao.mapper.AuthorMapper;
import ru.book.catalog.model.Author;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorJDBC implements AuthorRepository{
    private static final RowMapper<Author> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Author.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;


    public AuthorJDBC(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate()).withTableName("authors")
        .usingGeneratedKeyColumns("author_id");
    }

    @Override
    public Author save(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("author_id", author.getAuthorId());
        params.put("name", author.getName());
        params.put("last_name", author.getLastName());
        params.put("genre_id", author.getGenre().getId());
        if (author.isNew()) {
            Number number = simpleJdbcInsert.executeAndReturnKey(params);
            author.setAuthorId(number.longValue());
        } else {
            namedParameterJdbcTemplate.update("UPDATE authors SET name = :name, last_name = :last_name, genre_id = :genre_id, " +
                    "WHERE author_id = :author_id", params);
        }
        return author;

    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.queryForObject("SELECT author_id, name, last_name, genre_id FROM authors " +
                "WHERE author_id = :id", params, new AuthorMapper());
    }

    @Override
    public Author getByName(String name, String lastName) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("lastName", lastName);
        return namedParameterJdbcTemplate.queryForObject("SELECT author_id, name, last_name, genre_id FROM authors " +
                "WHERE name = :name AND last_name = :lastName", params, new AuthorMapper());
    }

    @Override
     public List<Author> getAll() {
       return namedParameterJdbcTemplate.query("SELECT author_id, name, last_name, genre_id FROM authors", new AuthorMapper());
    }

    @Override
    public boolean delete(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.update("DELETE FROM authors WHERE author_id =:id", params) != 0 ;
    }
}
