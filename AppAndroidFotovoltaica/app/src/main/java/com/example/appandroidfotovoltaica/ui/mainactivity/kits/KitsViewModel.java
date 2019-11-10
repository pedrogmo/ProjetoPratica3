package com.example.appandroidfotovoltaica.ui.mainactivity.kits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KitsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public KitsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is kits fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
