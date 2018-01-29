package io.github.andyradionov.googlebookssearch.model;

import java.util.List;

import io.github.andyradionov.googlebookssearch.model.dto.Book;


/**
 * @author Andrey Radionov
 */

public interface BooksDataCallback {

    void onSuccessLoad(List<Book> books);

    void onErrorLoad();
}
