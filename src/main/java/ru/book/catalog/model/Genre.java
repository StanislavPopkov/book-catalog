package ru.book.catalog.model;

import java.util.Arrays;

public enum Genre {
    MYSTERY(1),
    FICTION(2),
    NONFICTION(3),
    UNKNOWN(4);

    private long id;

    Genre(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static Genre getGenre(long id) {
        return Arrays.stream(values()).filter(genre -> id == genre.getId()).findFirst().orElseThrow();
    }
}
