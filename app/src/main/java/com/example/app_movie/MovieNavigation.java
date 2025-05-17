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


//        navView— это прям объект «панель‑с‑иконками‑внизу»
        BottomNavigationView navView = binding.navView;

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_bottom_navigation);
        // Настраиваем NavController
        // navController умеет по команде: «Перейди к экрану X» — заменить текущий фрагмент нужным.
        NavController navController = navHostFragment.getNavController();

//        Когда пользователь нажмёт «Фильмы», меню пошлёт сигнал в NavController.
//                NavController вставит нужную страницу‑фрагмент в рамку.

        NavigationUI.setupWithNavController(navView, navController);
    }
}
