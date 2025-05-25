package com.example.app_movie.ui.movie;

import com.example.app_movie.ui.movie.dto.GenreResponse;
import com.example.app_movie.ui.movie.dto.MovieDetailResponse;
import com.example.app_movie.ui.movie.dto.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {

    // https://api.themoviedb.org/3/movie/popular?api_key=KEY&language=ru
    @GET("movie/popular")
    Call<MovieResponse> getPopular(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("page") int page);

    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String lang);

    @GET("movie/{movie_id}")
    Call<MovieDetailResponse> getMovieDetails(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("append_to_response") String appendToResponse
    );

}
