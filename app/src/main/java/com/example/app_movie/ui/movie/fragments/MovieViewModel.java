package com.example.app_movie.ui.movie.fragments;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.app_movie.db.entity.Movie;
import com.example.app_movie.ui.movie.MovieApiRepository;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private MovieApiRepository repo;

    private final MediatorLiveData<List<Movie>> out = new MediatorLiveData<>();

    public void initContext(Context context)
    {

        repo = new MovieApiRepository(context);
        repo.refresh();

        Observer<List<Movie>> recompute = o -> {

            if (o == null || o.isEmpty()) return;
            out.setValue(o);
        };

        out.addSource(repo.getMovies(),  recompute);
    }

    public MovieViewModel() {

    }
    public LiveData<List<Movie>> items = out;

    public void refresh() {
        repo.refresh();
    }

}
