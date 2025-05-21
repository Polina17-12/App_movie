package com.example.app_movie.ui.movie.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_movie.R;
import com.example.app_movie.db.entity.Movie;
import com.example.app_movie.ui.movie.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.VH> {

    private static final String IMG = "https://image.tmdb.org/t/p/w500";
    private final List<Movie> data = new ArrayList<>();


    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    private OnItemClickListener listener;


    public void setOnItemClickListener(OnItemClickListener l) {
        this.listener = l;
    }


    public void submit(List<Movie> list) {
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
        Movie it = data.get(pos);

        h.title.setText(it.getTitle());
        h.genre.setText(it.getGenres());
        h.year.setText(it.getYear()!=null && it.getYear().length()>=4
                ? it.getYear().substring(0,4)
                : "—");
        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(it);
        });

        GlideApp.with(h.poster)
                .load(IMG + it.getPosterUrl())
                .placeholder(R.drawable.ic_notifications_black_24dp)
                .error(R.drawable.ic_notifications_black_24dp)
                .into(h.poster);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
