package com.example.app_movie.ui.movie;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@GlideModule
public final class MovieGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(
            @NonNull Context context,
            @NonNull Glide glide,
            @NonNull Registry registry) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();

        registry.replace(
                GlideUrl.class,
                InputStream.class,
                new OkHttpUrlLoader.Factory(okHttpClient));
    }


    @Override public boolean isManifestParsingEnabled() { return false; }
}