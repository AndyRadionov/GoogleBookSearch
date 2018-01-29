package io.github.andyradionov.googlebookssearch.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import io.github.andyradionov.googlebookssearch.R;
import io.github.andyradionov.googlebookssearch.model.dto.Book;

/**
 * @author Andrey Radionov
 */

public class BooksAdapter extends ArrayAdapter<Book> {

    private static final String TAG = BooksAdapter.class.getSimpleName();
    private static final String AUTHOR_DATE_FORMAT = "%s (%s)";
    private static final String PRICE_FORMAT = "%.2f %s";

    private int maxTitleLength;
    private int maxAuthorLength;
    private String titleFormat;
    private String authorFormat;

    public BooksAdapter(@NonNull Context context) {
        super(context, R.layout.item_book, new ArrayList<>());
        Log.d(TAG, "BooksAdapter constructor");

        maxTitleLength = context.getResources().getInteger(R.integer.max_title_length);
        maxAuthorLength = context.getResources().getInteger(R.integer.max_author_length);
        titleFormat = context.getString(R.string.title_format);
        authorFormat = context.getString(R.string.author_format);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View listItemView, @NonNull ViewGroup parent) {
        Log.d(TAG, "getView");
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_book, parent, false);
        }

        Book currentBook = getItem(position);

        if (currentBook == null || currentBook.getVolumeInfo() == null) {
            return listItemView;
        }

        setImage(listItemView, currentBook);
        setTitle(listItemView, currentBook);
        setAuthorAndYear(listItemView, currentBook);
        setPrice(listItemView, currentBook);

        return listItemView;
    }

    private void setImage(View listItemView, Book currentBook) {
        if (!currentBook.getVolumeInfo().hasThumbnail()) {
            return;
        }

        ImageView cover = listItemView.findViewById(R.id.iv_book_cover);
        Picasso.with(getContext())
                .load(currentBook.getVolumeInfo().getThumbnail())
                .into(cover);
    }

    private void setTitle(View listItemView, Book currentBook) {
        TextView title = listItemView.findViewById(R.id.tv_book_title);

        String bookTitle = formatTitle(currentBook.getVolumeInfo().getTitle());
        title.setText(bookTitle);
    }

    private void setAuthorAndYear(View listItemView, Book currentBook) {
        if (currentBook.getVolumeInfo().getAuthors() == null
                || currentBook.getVolumeInfo().getAuthors()[0] == null) {
            return;
        }

        TextView authorAndDate = listItemView.findViewById(R.id.tv_book_author_date);
        String authorName = formatAuthor(currentBook.getVolumeInfo().getAuthors()[0]);
        String date = currentBook.getVolumeInfo().getPublishedDate();

        String authorDate = date == null
                ? authorName
                : String.format(AUTHOR_DATE_FORMAT, authorName, date);

        authorAndDate.setText(authorDate);
    }

    private void setPrice(View listItemView, Book currentBook) {
        TextView priceDisplay = listItemView.findViewById(R.id.tv_book_price);

        if (!currentBook.getSaleInfo().hasPrice()) {
            priceDisplay.setVisibility(View.GONE);
            return;
        }

        priceDisplay.setVisibility(View.VISIBLE);

        double price = currentBook.getSaleInfo().getAmount();
        String code = currentBook.getSaleInfo().getCurrencyCode();

        priceDisplay.setText(String.format(Locale.getDefault(), PRICE_FORMAT, price, code));
    }

    private String formatTitle(String title) {
        return title.length() > maxTitleLength ? String.format(titleFormat, title) : title;
    }

    private String formatAuthor(String author) {
        return author.length() > maxAuthorLength ? String.format(authorFormat, author) : author;
    }
}
