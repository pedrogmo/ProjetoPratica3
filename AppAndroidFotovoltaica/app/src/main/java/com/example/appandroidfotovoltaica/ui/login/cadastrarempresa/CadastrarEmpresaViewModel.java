package com.example.appandroidfotovoltaica.ui.login.cadastrarempresa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CadastrarEmpresaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CadastrarEmpresaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is cadastrar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
