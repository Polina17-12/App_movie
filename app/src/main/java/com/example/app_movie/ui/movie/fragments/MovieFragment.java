package com.example.app_movie.ui.movie.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_movie.R;
import com.example.app_movie.ui.movie.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MovieFragment extends Fragment {

    private MovieViewModel vm;
    private MovieAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle b) {

        adapter = new MovieAdapter();
        SharedViewModel sharedVM =
                new ViewModelProvider(requireActivity())
                        .get(SharedViewModel.class);

        adapter.setOnItemClickListener(movie -> {
            sharedVM.select(movie);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.navigation_selected);
        });
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v,@Nullable Bundle b) {
        super.onViewCreated(v,b);

        RecyclerView rv = v.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        rv.setAdapter(adapter);


        adapter.setOnItemClickListener(movie -> {
            SharedViewModel sharedVM =
                    new ViewModelProvider(requireActivity())
                            .get(SharedViewModel.class);
            sharedVM.select(movie);

            BottomNavigationView navView =
                    requireActivity().findViewById(R.id.nav_view);
            navView.setSelectedItemId(R.id.navigation_selected);
        });


        vm = new ViewModelProvider(this).get(MovieViewModel.class);
        vm.initContext(getActivity());

        vm.items.observe(getViewLifecycleOwner(), adapter::submit);

        if (vm.items.getValue()==null) vm.refresh();
    }
}
