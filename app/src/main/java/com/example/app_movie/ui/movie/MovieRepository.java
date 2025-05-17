package com.example.app_movie.ui.movie;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static final String KEY = "46b36c05174f049d768ea6a97d23ce56";
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

    private final MutableLiveData<Map<Integer,String>> genres = new MutableLiveData<>();

    public LiveData<List<Movie>> getMovies() { return movies; }
    public LiveData<Map<Integer,String>> getGenres() { return genres; }

    /** Загружаем сразу два списка параллельно */
    public void refresh() {
        loadGenres();     // 1) словарь жанров
        loadMovies();     // 2) список фильмов
    }

    private void loadGenres() {
        ApiClient.getApi().getGenres(KEY, "ru")
                .enqueue(new Callback<GenreResponse>() {
                    @Override public void onResponse(Call<GenreResponse> c,
                                                     Response<GenreResponse> r) {
                        if (r.isSuccessful() && r.body()!=null) {
                            Map<Integer,String> map = new HashMap<>();
                            for (Genre g: r.body().genres) map.put(g.id, g.name);
                            genres.postValue(map);
                        }
                    }
                    @Override public void onFailure(Call<GenreResponse> c, Throwable t) { }
                });
    }

    public void loadMovies() {
        ApiClient.getApi().getPopular(KEY, "ru", 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override public void onResponse(Call<MovieResponse> call,
                                                     Response<MovieResponse> resp) {
                        if (resp.isSuccessful() && resp.body() != null) {
                            movies.postValue(resp.body().results);
                        }
                    }
                    @Override public void onFailure(Call<MovieResponse> call, Throwable t) {
                        // обработка ошибки
                        Log.e("MovieRepository", t.getMessage());
                    }
                });
    }
}
