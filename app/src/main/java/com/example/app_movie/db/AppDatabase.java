package com.example.app_movie.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.app_movie.db.dao.MovieDao;
import com.example.app_movie.db.dao.UserDao;
import com.example.app_movie.db.entity.Movie;
import com.example.app_movie.db.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract MovieDao movieDao();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    databaseWriteExecutor.execute(() -> {
                                        UserDao dao = INSTANCE.userDao();
                                        dao.insert(new User("u", "p"));
                                        dao.insert(new User("user1", "p1"));
                                        dao.insert(new User("user2", "p2"));
                                        dao.insert(new User("user3", "p3"));
                                        dao.insert(new User("user4", "p4"));
                                        dao.insert(new User("user5", "p5"));
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}