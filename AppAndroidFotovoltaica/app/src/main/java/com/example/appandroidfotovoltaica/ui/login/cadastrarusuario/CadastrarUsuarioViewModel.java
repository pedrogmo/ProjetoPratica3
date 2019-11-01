package com.example.appandroidfotovoltaica.ui.login.cadastrarusuario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CadastrarUsuarioViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CadastrarUsuarioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is cadastrar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
