package com.example.app_movie.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.app_movie.db.AppDatabase;
import com.example.app_movie.db.dao.MovieDao;
import com.example.app_movie.db.entity.Movie;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class MovieDbRepository {
    private final MovieDao movieDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public MovieDbRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        movieDao = db.movieDao();
    }

    public void selectAll(Consumer<List<Movie>> cb) {
        executor.execute(() -> {

            var res = movieDao.getAll();
            mainHandler.post(() -> cb.accept(res));
        });
    }

    public void select(int id,Consumer<Movie> cb) {
        executor.execute(() -> {

            var res = movieDao.getById(id);
            mainHandler.post(() -> cb.accept(res));
        });
    }

    public void insert(Movie movie, Consumer<Movie> cb) {
        executor.execute(() -> {

            movieDao.insert(movie);
            mainHandler.post(() -> cb.accept(movie));
        });
    }


}
