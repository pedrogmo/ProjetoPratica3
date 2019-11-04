package com.example.appandroidfotovoltaica.ui.mainactivity.propostas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PropostasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PropostasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is propostas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}