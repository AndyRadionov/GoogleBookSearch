package io.github.andyradionov.googlebookssearch.ui;

import java.util.List;

import io.github.andyradionov.googlebookssearch.model.dto.Book;

/**
 * @author Andrey Radionov
 */

public interface BooksView {

    void showBooks(List<Book> books);

    void showErrorMsg();
}
