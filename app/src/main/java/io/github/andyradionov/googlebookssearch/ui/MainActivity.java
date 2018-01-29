package io.github.andyradionov.googlebookssearch.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.github.andyradionov.googlebookssearch.R;
import io.github.andyradionov.googlebookssearch.app.App;
import io.github.andyradionov.googlebookssearch.model.dto.Book;
import io.github.andyradionov.googlebookssearch.presenter.BooksPresenter;

public class MainActivity extends AppCompatActivity implements BooksView {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String QUERY_KEY = "query_key";

    private String mStringQuery;
    private EditText mSearchBox;
    private AbsListView mBookList;
    private ProgressBar mLoadingIndicator;
    private TextView mEmptyView;
    private BooksPresenter mBooksPresenter;
    private BooksAdapter mBooksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBox = findViewById(R.id.et_search_query);
        mBookList = findViewById(R.id.books_container);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mEmptyView = findViewById(android.R.id.empty);

        mBooksPresenter = App.getBooksPresenter();

        mBooksAdapter = new BooksAdapter(this);
        mBookList.setAdapter(mBooksAdapter);

        setOnItemOnClickListener();
        setKeyListener();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        if (mStringQuery != null && !mStringQuery.isEmpty()) {
            outState.putString(QUERY_KEY, mStringQuery);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        mStringQuery = savedInstanceState.getString(QUERY_KEY);
        if (mStringQuery != null) {
            presentBooks();
        }

    }

    @Override
    public void showBooks(List<Book> books) {
        Log.d(TAG, "showBooks");
        if (books == null || books.isEmpty()) {
            showErrorMsg();
            return;
        }

        mBookList.setEmptyView(null);

        mBooksAdapter.clear();
        mBooksAdapter.addAll(books);
        mBookList.smoothScrollToPosition(0);

        mLoadingIndicator.setVisibility(View.GONE);
        mBookList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMsg() {
        Log.d(TAG, "showErrorMsg");
        mEmptyView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.GONE);
        mBookList.setVisibility(View.VISIBLE);

        mBooksAdapter.clear();
        mBookList.setEmptyView(mEmptyView);
    }

    public void onSearchClick(View view) {
        Log.d(TAG, "onSearchClick");
        hideKeyboard();

        if (App.isInternetAvailable(this)) {
            String searchQuery = mSearchBox.getText().toString().toLowerCase().trim();
            if (searchQuery.isEmpty()) {
                showErrorMsg();
                return;
            }
            mStringQuery = searchQuery;
            presentBooks();
        } else {
            Toast.makeText(this, R.string.no_internet_msg, Toast.LENGTH_LONG).show();
        }
    }

    private void presentBooks() {
        Log.d(TAG, "presentBooks");
        mEmptyView.setVisibility(View.GONE);
        mBookList.setVisibility(View.GONE);
        mLoadingIndicator.setVisibility(View.VISIBLE);

        mBooksPresenter.findBooks(this, mStringQuery);
    }

    private void setOnItemOnClickListener() {
        Log.d(TAG, "setOnItemOnClickListener");
        mBookList.setOnItemClickListener((parent, view, position, id) -> {
            List<Book> books = mBooksPresenter.getCurrentBooks();
            String bookUrl = books.get(position).getVolumeInfo().getInfoLink();

            Intent startGooglePlay = new Intent(Intent.ACTION_VIEW, Uri.parse(bookUrl));
            startActivity(startGooglePlay);
        });
    }

    private void setKeyListener() {
        mSearchBox.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearchClick(v);
                return true;
            }
            return false;
        });
    }

    private void hideKeyboard() {
        Log.d(TAG, "hideKeyboard");
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
