package com.example.week7_1.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("https://m.naver.com");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void changeText(String str){
        mText.setValue(str);
    }

}