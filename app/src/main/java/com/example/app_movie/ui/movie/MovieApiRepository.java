package com.example.app_movie.ui.movie;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_movie.data.MovieDbRepository;
import com.example.app_movie.db.entity.Movie;
import com.example.app_movie.ui.movie.dto.Genre;
import com.example.app_movie.ui.movie.dto.GenreResponse;
import com.example.app_movie.ui.movie.dto.MovieDetailResponse;
import com.example.app_movie.ui.movie.dto.MovieResponse;
import com.example.app_movie.ui.movie.dto.MovieResponseDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieApiRepository {

    private static final String KEY = "46b36c05174f049d768ea6a97d23ce56";

    //чтобы передавать список фильмов
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();


    //бд с фильмами
    private final MovieDbRepository movieDbRepository;

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }


    //жанры
    private Map<Integer, String> genreMap;
    //все, кроме актеров
    private List<MovieResponseDto> movieList;
    //описание,
    private final Map<Integer, MovieDetailResponse> detailMap = new ConcurrentHashMap<>();

    private final AtomicInteger pendingDetails = new AtomicInteger(0);


    //тут context нужен, чтобы доступ к movieDbRepository
    public MovieApiRepository(Context context) {
        movieDbRepository = new MovieDbRepository(context);
    }


    //
    public void refresh() {
        movieDbRepository.selectAll((l) ->
        {
            if (l == null || l.isEmpty()) {
                realRefresh();
            }
            movies.postValue(l);
        });
    }

    public void realRefresh() {

        genreMap = null;
        movieList = null;
        detailMap.clear();
        pendingDetails.set(0);

        loadGenres();
        loadMovies();
    }

    private void loadGenres() {
        ApiClient.getApi().getGenres(KEY, "ru")
                .enqueue(new Callback<GenreResponse>() {
                    @Override
                    public void onResponse(Call<GenreResponse> call,
                                           Response<GenreResponse> resp) {
                        if (resp.isSuccessful() && resp.body() != null) {
                            Map<Integer, String> map = new HashMap<>();
                            for (Genre g : resp.body().genres) {
                                map.put(g.id, g.name);
                            }
                            genreMap = map;
                            // функция собирает все на свете в movie
                            tryEmit();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenreResponse> c, Throwable t) {
                        genreMap = new HashMap<>();
                        tryEmit();
                    }
                });
    }

    private void loadMovies() {
        ApiClient.getApi().getPopular(KEY, "ru", 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> c,
                                           Response<MovieResponse> r) {
                        if (r.isSuccessful() && r.body() != null) {
                            movieList = r.body().results;
                            // установим, сколько деталей нам ждать (счетчик)
                            pendingDetails.set(movieList.size());
                            // запустим загрузку деталей по каждому
                            for (MovieResponseDto dto : movieList) {
                                loadMovieDetail(dto.id, dto);
                            }
                            tryEmit(); // на случай, если size=0
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> c, Throwable t) {
                        movieList = new ArrayList<>();
                        tryEmit();
                    }
                });
    }


    //4-5
    private void loadMovieDetail(int movieId, MovieResponseDto dto) {
        ApiClient.getApi()
                .getMovieDetails(movieId, KEY, "ru", "credits")
                .enqueue(new Callback<MovieDetailResponse>() {
                    @Override
                    public void onResponse(Call<MovieDetailResponse> c,
                                           Response<MovieDetailResponse> r) {
                        if (r.isSuccessful() && r.body() != null) {
                            detailMap.put(movieId, r.body());
                        }
                        pendingDetails.decrementAndGet();
                        tryEmit();
                    }

                    @Override
                    public void onFailure(Call<MovieDetailResponse> c, Throwable t) {
                        pendingDetails.decrementAndGet();
                        tryEmit();
                    }
                });
    }

    private void tryEmit() {
        // все три условия:
        if (genreMap != null
                && movieList != null
                //счетчик равен 0 (в функции loadMovies)
                && pendingDetails.get() == 0) {

            List<Movie> out = new ArrayList<>(movieList.size());
            for (MovieResponseDto dto : movieList) {
                //вся инфа о фильме
                MovieDetailResponse detail = detailMap.get(dto.id);

//были странные циклы for
                String genresStr = String.join(", ", dto.genreIds.stream().filter(id -> genreMap.containsKey(id)).map(genreMap::get).collect(Collectors.toList()));
                String actors = detail != null ? String.join(", ", detail.credits.cast.stream().map(a -> a.name).collect(Collectors.toList())): "";


                // описание может лежать в detail.overview
                String overview = detail != null ? detail.overview : "";

                out.add(new Movie(
                        dto.id,
                        dto.title,
                        overview,
                        dto.releaseDate,
                        dto.posterPath,  // или как у вас
                        genresStr,
                        actors
                ));
            }
            for (var m : out) {
                movieDbRepository.insert(m, (a) -> {
                });
            }
            movies.postValue(out);
        }
    }
}
