package com.example.appandroidfotovoltaica.ui.login.cadastrar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CadastrarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CadastrarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is cadastrar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
