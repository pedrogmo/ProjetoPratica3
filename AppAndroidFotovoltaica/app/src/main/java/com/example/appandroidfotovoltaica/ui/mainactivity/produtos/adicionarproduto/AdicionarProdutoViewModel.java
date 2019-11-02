package com.example.appandroidfotovoltaica.ui.mainactivity.produtos.adicionarproduto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdicionarProdutoViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AdicionarProdutoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is adicionarproduto fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
