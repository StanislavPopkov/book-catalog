package ru.book.catalog.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.book.catalog.model.Author;
import ru.book.catalog.model.Book;
import ru.book.catalog.model.Genre;
import ru.book.catalog.service.AuthorService;
import ru.book.catalog.service.BookService;

@ShellComponent
public class Console {
    private final BookService bookService;
    private final AuthorService authorService;

    public Console(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    //add-book Example Alex Qwe NONFICTION
    @ShellMethod(value = "Create book", key = {"add-book"})
    public String addBook(String bookName, String name, String lastName, String genre) {
        Genre genreValue = null;
        try {
            genreValue = Genre.valueOf(genre.toUpperCase());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Genre not found";
        }
        Author author = authorService.getByName(name, lastName);
        Book book = null;
        if (author == null) {
            author = authorService.save(new Author(0, name, lastName, null, genreValue));
        }
        book = bookService.save(new Book(0, bookName, author, genreValue));
        return String.format("Book with bookName %s and id %d added", bookName, book.getBookId());
    }

    @ShellMethod(value = "Find book", key = {"find-book"})
    public String findBook(String bookName) {
        Book result = bookService.getByName(bookName);
        return result != null ? result.toString() : "Book not found";
    }

    @ShellMethod(value = "Get all books", key = {"get-all-books"})
    public String getAllBooks() {
        return "Books stab";
    }

    @ShellMethod(value = "Create author", key = {"add-author"})
    public String addAuthor(String name, String lastName, String genre) {
        Genre genreValue = null;
        try {
            genreValue = Genre.valueOf(genre.toUpperCase());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Genre not found";
        }
        Author author = authorService.save(new Author(0, name, lastName, null, genreValue));
        return String.format("Author with name %s, lastname %s and id %d added", name, lastName, author.getAuthorId());
    }

    @ShellMethod(value = "Find author", key = {"find-author"})
    public String findAuthor(String name, String lastName) {
        Author result = authorService.getByName(name, lastName);
        return result != null ? result.toString() : "Author not found";
    }

    @ShellMethod(value = "Get all authors", key = {"get-all-authors"})
    public String getAllAuthors() {
        return authorService.getAll().toString();
    }
}
