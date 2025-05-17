package com.example.app_movie.ui.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreResponse {
    @SerializedName("genres") public List<Genre> genres;
}
