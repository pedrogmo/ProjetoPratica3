package com.example.appandroidfotovoltaica.ui.mainactivity.kits.adicionarkit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdicionarKitViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AdicionarKitViewModel () {
        mText = new MutableLiveData<>();
        mText.setValue("This is adicionar kit fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
