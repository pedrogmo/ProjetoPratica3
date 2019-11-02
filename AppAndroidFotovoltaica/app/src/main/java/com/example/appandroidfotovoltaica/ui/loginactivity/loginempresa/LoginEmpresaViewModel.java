package com.example.appandroidfotovoltaica.ui.loginactivity.loginempresa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginEmpresaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LoginEmpresaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is clientes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}