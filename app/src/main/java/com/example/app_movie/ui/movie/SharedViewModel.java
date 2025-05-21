package com.example.app_movie.ui.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_movie.db.entity.Movie;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Movie> selectedMovie = new MutableLiveData<>();

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }

    public void select(Movie movie) {
        selectedMovie.setValue(movie);
    }
}