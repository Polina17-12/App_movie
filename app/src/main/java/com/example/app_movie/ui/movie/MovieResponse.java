package com.example.app_movie.ui.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results") public List<Movie> results;
}
