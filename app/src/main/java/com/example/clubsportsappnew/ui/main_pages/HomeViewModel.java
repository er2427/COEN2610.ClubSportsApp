package com.example.clubsportsappnew.ui.main_pages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome to Marquette Club Sports");
    }

    public LiveData<String> getText() {
        return mText;
    }
}