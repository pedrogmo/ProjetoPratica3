package com.example.appandroidfotovoltaica.ui.mainactivity.kits.kitindividual;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KitIndividualViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public KitIndividualViewModel () {
        mText = new MutableLiveData<>();
        mText.setValue("This is kit individual fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
