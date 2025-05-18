package com.example.app_movie.ui.movie.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Credits {
    @SerializedName("cast")
    public List<Cast> cast;       // список актёров
}
