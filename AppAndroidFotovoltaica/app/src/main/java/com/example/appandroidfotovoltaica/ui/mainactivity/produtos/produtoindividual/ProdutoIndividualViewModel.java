package com.example.appandroidfotovoltaica.ui.mainactivity.produtos.produtoindividual;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProdutoIndividualViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ProdutoIndividualViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is produtoindividual fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
