package com.example.app_movie.ui.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_movie.R;

public class MovieFragment extends Fragment {

    private MovieViewModel vm;
    private MovieAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle b) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v,@Nullable Bundle b) {
        super.onViewCreated(v,b);

        RecyclerView rv = v.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MovieAdapter();
        rv.setAdapter(adapter);

        vm = new ViewModelProvider(this).get(MovieViewModel.class);
        vm.items.observe(getViewLifecycleOwner(), adapter::submit);

        if (vm.items.getValue()==null) vm.refresh();
    }
}
