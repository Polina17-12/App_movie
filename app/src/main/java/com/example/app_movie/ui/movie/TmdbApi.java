package com.example.app_movie.ui.movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbApi {

    // https://api.themoviedb.org/3/movie/popular?api_key=YOUR_KEY&language=ru
    @GET("movie/popular")
    Call<MovieResponse> getPopular(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("page") int page);

    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String lang);
}
