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

    //место, куда фильмы будут публиковаться
    private final MediatorLiveData<List<Movie>> out = new MediatorLiveData<>();

    //нужна, чтобы в ApiRepository подключение к бд создать
    public void initContext(Context context)
    {
        //херня, которая умеет подтягивать данные
        repo = new MovieApiRepository(context);
        repo.refresh();
        //когда данные обновятся, его вызовут с этими данными
        Observer<List<Movie>> recompute = o -> {

            if (o == null || o.isEmpty()) return;          // фильмов ещё нет — рано
            out.setValue(o);
        };
//подписываемся, что от getMovie мы получим фильмы, когда получили выполняем следующее
        out.addSource(repo.getMovies(),  recompute);
    }

    public MovieViewModel() {

    }
    public LiveData<List<Movie>> items = out;
    //public void refresh() { repo.loadMovies(); }
    public void refresh() {
        repo.refresh();                   // ← теперь тоже оба списка
    }

}
