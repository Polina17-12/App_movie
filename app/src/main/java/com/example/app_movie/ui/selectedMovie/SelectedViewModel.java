package com.example.app_movie.ui.selectedMovie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectedViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SelectedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Здесь будут выбранные фильмы");
    }

    public LiveData<String> getText() {
        return mText;
    }
}