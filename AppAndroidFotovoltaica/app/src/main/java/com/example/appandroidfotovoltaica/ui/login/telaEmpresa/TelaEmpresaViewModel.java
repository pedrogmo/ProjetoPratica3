package com.example.appandroidfotovoltaica.ui.login.telaEmpresa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TelaEmpresaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TelaEmpresaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is clientes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}