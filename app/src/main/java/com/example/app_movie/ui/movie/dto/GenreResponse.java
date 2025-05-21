package com.example.app_movie.ui.movie.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;


//список
public class GenreResponse {
    @SerializedName("genres") public List<Genre> genres;
}
