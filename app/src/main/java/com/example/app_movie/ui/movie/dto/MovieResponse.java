package com.example.app_movie.ui.movie.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//список объектов  филмов
public class MovieResponse {
    @SerializedName("results") public List<MovieResponseDto> results;
}
