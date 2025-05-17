package com.example.app_movie.ui.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    @SerializedName("id")            public int id;
    @SerializedName("title")         public String title;
    @SerializedName("release_date")  public String releaseDate;  // "2024-03-12"
    @SerializedName("genre_ids")     public List<Integer> genreIds;
    @SerializedName("poster_path")   public String posterPath;
}