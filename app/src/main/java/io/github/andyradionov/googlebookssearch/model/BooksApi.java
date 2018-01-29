package io.github.andyradionov.googlebookssearch.model;

import io.github.andyradionov.googlebookssearch.model.dto.BooksResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Andrey Radionov
 */

public interface BooksApi {

    @GET("volumes")
    Call<BooksResponseDTO> findAllBooksByQuery(@Query("q") String query,
                                               @Query("maxResults") int maxResultSize);
}
