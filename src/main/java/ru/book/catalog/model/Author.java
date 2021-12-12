package ru.book.catalog.model;

public class Author {
    private long authorId;
    private String name;
    private String lastName;
    private Book book;
    private Genre genre;

    public Author() {
    }

    public Author(long authorId, String name, String lastName, Book book, Genre genre) {
        this.authorId = authorId;
        this.name = name;
        this.lastName = lastName;
        this.book = book;
        this.genre = genre;
    }

    public long getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public boolean isNew() {
        return authorId == 0;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", LastName='" + lastName + '\'' +
                ", book=" + "book" +
                ", genre=" + genre +
                '}';
    }
}
