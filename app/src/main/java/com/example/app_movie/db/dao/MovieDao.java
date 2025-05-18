package com.example.app_movie.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.app_movie.db.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    Movie getById(long id);

    @Query("SELECT * FROM movies ORDER BY title ASC")
    List<Movie> getAll();
}