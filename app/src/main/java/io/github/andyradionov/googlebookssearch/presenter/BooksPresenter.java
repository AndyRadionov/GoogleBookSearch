package io.github.andyradionov.googlebookssearch.presenter;

import android.util.Log;

import java.util.List;

import io.github.andyradionov.googlebookssearch.model.BooksData;
import io.github.andyradionov.googlebookssearch.model.BooksDataCallback;
import io.github.andyradionov.googlebookssearch.model.dto.Book;
import io.github.andyradionov.googlebookssearch.ui.BooksView;


/**
 * @author Andrey Radionov
 */

public class BooksPresenter implements BooksDataCallback {

    private static final String TAG = BooksPresenter.class.getSimpleName();

    private String previousQuery;
    private BooksData booksData;
    private BooksView booksView;

    public BooksPresenter(BooksData booksData) {
        Log.d(TAG, "BooksPresenter constructor");
        this.booksData = booksData;
    }

    public void findBooks(BooksView booksView, String query) {
        Log.d(TAG, "findBooks");
        this.booksView = booksView;
        if (previousQuery != null && query.equals(previousQuery)) {
            booksView.showBooks(booksData.getPreviousResult());
            return;
        }
        booksData.findAllBooksByQuery(query, this);
        previousQuery = query;
    }

    public List<Book> getCurrentBooks() {
        Log.d(TAG, "getCurrentBooks");
        return booksData.getPreviousResult();
    }

    @Override
    public void onSuccessLoad(List<Book> books) {
        Log.d(TAG, "onSuccessLoad");
        booksView.showBooks(books);
    }

    @Override
    public void onErrorLoad() {
        Log.d(TAG, "onErrorLoad");
        booksView.showErrorMsg();
    }
}
