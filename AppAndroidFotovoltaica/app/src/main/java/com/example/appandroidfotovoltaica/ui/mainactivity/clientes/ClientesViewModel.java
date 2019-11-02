package com.example.appandroidfotovoltaica.ui.mainactivity.clientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClientesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClientesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the main clientes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}