package com.example.appandroidfotovoltaica.ui.produtos.adicionarproduto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdicionarProdutoViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AdicionarProdutoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is adicionar produto fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
