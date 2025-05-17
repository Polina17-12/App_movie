package com.example.app_movie.ui.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_movie.R;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.VH> {

    private static final String IMG = "https://image.tmdb.org/t/p/w500";
    private final List<MovieViewModel.MovieItem> data = new ArrayList<>();

    public void submit(List<MovieViewModel.MovieItem> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView  title, genre, year;

        VH(View v) {
            super(v);
            poster = v.findViewById(R.id.poster);
            title  = v.findViewById(R.id.title);
            genre  = v.findViewById(R.id.genre);
            year   = v.findViewById(R.id.year);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h,int pos){
        MovieViewModel.MovieItem it = data.get(pos);
        Movie m = it.movie;

        h.title.setText(m.title);
        h.genre.setText(it.genreText);
        h.year.setText(m.releaseDate!=null && m.releaseDate.length()>=4
                ? m.releaseDate.substring(0,4)
                : "—");

        GlideApp.with(h.poster)
                .load(IMG + m.posterPath)
                .placeholder(R.drawable.ic_notifications_black_24dp)
                .error(R.drawable.ic_notifications_black_24dp)
                .into(h.poster);                         // асинхронно загружает и кеширует картинку
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
