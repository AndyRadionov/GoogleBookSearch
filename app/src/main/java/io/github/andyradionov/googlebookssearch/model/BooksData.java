package io.github.andyradionov.googlebookssearch.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.github.andyradionov.googlebookssearch.app.App;
import io.github.andyradionov.googlebookssearch.app.AppPreferences;
import io.github.andyradionov.googlebookssearch.model.dto.Book;
import io.github.andyradionov.googlebookssearch.model.dto.BooksResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Andrey Radionov
 */

public class BooksData {
    private static final String TAG = BooksData.class.getSimpleName();
    private BooksApi booksApi;
    private List<Book> previousResult;

    public BooksData() {
        this.booksApi = App.getApi();
    }

    public void findAllBooksByQuery(String query, final BooksDataCallback booksDataCallback) {
        Log.d(TAG, "findAllBooksByQuery");

        booksApi.findAllBooksByQuery(query, AppPreferences.MAX_RESULT_SIZE)
                .enqueue(new Callback<BooksResponseDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<BooksResponseDTO> call, @NonNull Response<BooksResponseDTO> response) {
                        if (response.body() == null) {
                            booksDataCallback.onErrorLoad();
                            return;
                        }
                        previousResult = response.body().getItems();
                        booksDataCallback.onSuccessLoad(previousResult);
                    }

                    @Override
                    public void onFailure(@NonNull Call<BooksResponseDTO> call, @NonNull Throwable t) {
                        booksDataCallback.onErrorLoad();
                    }
                });
    }

    public List<Book> getPreviousResult() {
        return previousResult;
    }
}
