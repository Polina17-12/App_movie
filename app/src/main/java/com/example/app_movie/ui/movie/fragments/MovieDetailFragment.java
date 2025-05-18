package com.example.app_movie.ui.movie.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_movie.R;
import com.example.app_movie.db.entity.Movie;
import com.example.app_movie.ui.movie.GlideApp;

public class MovieDetailFragment extends Fragment {
    private static final String ARG_MOVIE = "arg_movie";
    private static final String IMG_BASE = "https://image.tmdb.org/t/p/w500";

    private Movie movie;

    //новый объект в новом фрагменте
    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment f = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable(ARG_MOVIE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }


    //собирает из объекта данные, и распихивает поля из объекта в ui
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        ImageView ivPoster = v.findViewById(R.id.ivPoster);
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        TextView tvYear = v.findViewById(R.id.tvYear);
        TextView tvDesc = v.findViewById(R.id.tvDescription);
        TextView tvGenres = v.findViewById(R.id.tvGenres);
        TextView tvActors = v.findViewById(R.id.tvActors);

        if (movie != null) {
            tvTitle.setText(movie.getTitle());
            tvYear.setText(movie.getYear());
            tvDesc.setText(movie.getDescription());
            tvGenres.setText(movie.getGenres());
            tvActors.setText(movie.getActors());

            GlideApp.with(ivPoster)
                    .load(IMG_BASE + movie.getPosterUrl())
                    .placeholder(R.drawable.ic_notifications_black_24dp)
                    .error(R.drawable.ic_notifications_black_24dp)
                    .into(ivPoster);
        }
    }
}