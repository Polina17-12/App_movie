package com.example.app_movie.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.example.app_movie.db.AppDatabase;
import com.example.app_movie.db.dao.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
    }

    public void login(String username, String password, AuthCallback cb) {
        executor.execute(() -> {

            var res = userDao.getUser(username, password) != null;
            mainHandler.post(() -> cb.onResult(res));
        });
    }

    public interface AuthCallback {
        void onResult(@Nullable Boolean user);
    }
}