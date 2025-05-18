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
    private static volatile AppDatabase INSTANCE; //чтобы не создавать новую бд (синглтон)

    public abstract UserDao userDao();
    public abstract MovieDao movieDao(); //в apirepository когда загружаем, смотрим в movie таблице есть ли что-то

    private static final int NUMBER_OF_THREADS = 4; //можем в бд ходить в 4 потока, можно 4 операции одновременно делать
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS); //управляет количеством потоков (просто потоками)
    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) { //все потоки синхронизируются по объекту (пока у потока нет объекта, он ждет)
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // При первом создании базы наполняем начальными данными
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