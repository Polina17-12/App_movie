package com.example.app_movie.ui.movie;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieViewModel extends ViewModel {
    private final MovieRepository repo = new MovieRepository();

    //public final LiveData<List<MovieItem>> items;
    private final MediatorLiveData<List<MovieItem>> out = new MediatorLiveData<>();
    /** UI-ready объект */
    public static class MovieItem {
        public final Movie movie;
        public final String genreText;
        public MovieItem(Movie m, String g) { movie=m; genreText=g; }
    }

    public MovieViewModel() {
        repo.refresh();
        Observer<Object> recompute = o -> {
            List<Movie>            mv = repo.getMovies().getValue();
            Map<Integer,String> gmap = repo.getGenres().getValue();  // может быть null
            Log.i("!!!!!!!!!!", "hhh");

            if (mv == null) return;          // фильмов ещё нет — рано

            List<MovieItem> ready = new ArrayList<>();
            for (Movie m : mv) {
                String g = "—";
                if (gmap != null && !m.genreIds.isEmpty())
                    g = gmap.getOrDefault(m.genreIds.get(0), "Неизвестно");

                ready.add(new MovieItem(m, g));
            }
            out.setValue(ready);
        };

        out.addSource(repo.getMovies(),  recompute);
        out.addSource(repo.getGenres(),  recompute);
    }
    //public LiveData<List<Movie>> movies = repo.getMovies();
    public LiveData<List<MovieItem>> items = out;
    //public void refresh() { repo.loadMovies(); }
    public void refresh() {
        repo.refresh();                   // ← теперь тоже оба списка
    }
}
