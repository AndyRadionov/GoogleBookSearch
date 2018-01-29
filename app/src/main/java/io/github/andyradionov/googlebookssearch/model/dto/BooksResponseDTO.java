package io.github.andyradionov.googlebookssearch.model.dto;

import java.util.List;

/**
 * @author Andrey Radionov
 */

public class BooksResponseDTO {

    private List<Book> items;

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }
}
