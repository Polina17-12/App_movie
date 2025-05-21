package com.example.app_movie.ui.movie.dto;

import com.google.gson.annotations.SerializedName;

public class MovieDetailResponse {
    @SerializedName("id")            public int id;
    @SerializedName("title")         public String title;
    @SerializedName("release_date")  public String releaseDate;
    @SerializedName("overview")      public String overview;
    @SerializedName("poster_path")   public String posterPath;

    @SerializedName("credits")       public Credits credits;
}
