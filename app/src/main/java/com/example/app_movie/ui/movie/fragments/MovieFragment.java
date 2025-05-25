package com.example.app_movie.ui.movie.fragments;

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
import com.example.app_movie.ui.movie.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MovieFragment extends Fragment {

    private MovieViewModel vm;
    private MovieAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private SharedViewModel sharedVM;
    private BottomNavigationView navView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);

        recyclerView = v.findViewById(R.id.recycler);
        fab = v.findViewById(R.id.fab_scroll_up);
        navView = requireActivity().findViewById(R.id.nav_view);
        sharedVM = new ViewModelProvider(requireActivity())
                .get(SharedViewModel.class);


        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(lm);
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);


        fab.setOnClickListener(__ -> recyclerView.smoothScrollToPosition(0));


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                // если самый первый элемент НЕ полностью виден — покажем кнопку
//                if (lm.findFirstCompletelyVisibleItemPosition() > 0)
                if (dy > 0)
                {
                    if (!fab.isShown()) fab.show();
                } else {
                    if (fab.isShown()) fab.hide();
                }
            }
        });


        adapter.setOnItemClickListener(movie -> {
            sharedVM.select(movie);
            navView.setSelectedItemId(R.id.navigation_selected);
        });

        //зашрузка данных
        vm = new ViewModelProvider(this).get(MovieViewModel.class);
        vm.initContext(requireActivity());
        vm.items.observe(getViewLifecycleOwner(), adapter::submit);
        if (vm.items.getValue() == null) vm.refresh();
    }
}