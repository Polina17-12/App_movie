package com.example.app_movie.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movies")
public class Movie implements Serializable {
    @PrimaryKey
    private long id;

    private String title;
    private String description;
    private String year;
    private String posterUrl;
    // новые поля
    private String genres;  // например: "Action, Drama, Sci-Fi"
    private String actors;  // например: "Leonardo DiCaprio, Joseph Gordon-Levitt"

    public Movie(long id, String title, String description, String year, String posterUrl, String genres,
                 String actors) {
//        this(title, description, year, posterUrl, genres, actors);
        this.id = id;
        this.title = title;
        this.description = description;
        this.year = year;
        this.posterUrl = posterUrl;
        this.genres = genres;
        this.actors = actors;
    }

//    public Movie(String title, String description, String year, String posterUrl, String genres,
//                 String actors) {
//
//    }

    // геттеры/сеттеры
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }
}
