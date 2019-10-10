package com.example.appandroidfotovoltaica.ui.adicionarcliente;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdicionarClienteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AdicionarClienteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is clientes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}