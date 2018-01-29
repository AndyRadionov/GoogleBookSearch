package io.github.andyradionov.googlebookssearch.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.gson.Gson;

import io.github.andyradionov.googlebookssearch.model.BooksApi;
import io.github.andyradionov.googlebookssearch.model.BooksData;
import io.github.andyradionov.googlebookssearch.presenter.BooksPresenter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Application class
 */

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static BooksApi booksApi;
    private static BooksPresenter booksPresenter;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();

        booksApi = createApi();
        booksPresenter = new BooksPresenter(new BooksData());
    }

    public static BooksApi getApi() {
        Log.d(TAG, "getApi");
        return booksApi;
    }

    public static BooksPresenter getBooksPresenter() {
        Log.d(TAG, "getBooksPresenter");
        return booksPresenter;
    }

    /**
     * Check if Internet is Available on device
     *
     * @param context of application
     * @return internet status
     */
    public static boolean isInternetAvailable(Context context) {
        Log.d(TAG, "isInternetAvailable");
        ConnectivityManager mConMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return mConMgr != null
                && mConMgr.getActiveNetworkInfo() != null
                && mConMgr.getActiveNetworkInfo().isAvailable()
                && mConMgr.getActiveNetworkInfo().isConnected();
    }

    private static BooksApi createApi() {
        return new Retrofit.Builder()
                .baseUrl(AppPreferences.BOOKS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build()
                .create(BooksApi.class);
    }
}
