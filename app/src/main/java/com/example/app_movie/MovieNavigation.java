package com.example.app_movie;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.app_movie.databinding.ActivityBottomNavigationBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MovieNavigation extends AppCompatActivity {

    private ActivityBottomNavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = binding.navView;

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_bottom_navigation);

        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(navView, navController);
    }
}
