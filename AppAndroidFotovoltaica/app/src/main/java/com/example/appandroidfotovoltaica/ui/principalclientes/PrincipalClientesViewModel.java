package com.example.appandroidfotovoltaica.ui.principalclientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrincipalClientesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PrincipalClientesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the main clientes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}