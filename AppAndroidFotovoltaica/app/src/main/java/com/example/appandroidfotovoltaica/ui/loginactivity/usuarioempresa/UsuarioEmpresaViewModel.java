package com.example.appandroidfotovoltaica.ui.loginactivity.usuarioempresa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UsuarioEmpresaViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public UsuarioEmpresaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is usuario empresa fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
