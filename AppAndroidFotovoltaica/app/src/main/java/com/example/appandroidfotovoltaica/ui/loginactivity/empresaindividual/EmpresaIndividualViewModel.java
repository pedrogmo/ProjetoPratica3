package com.example.appandroidfotovoltaica.ui.loginactivity.empresaindividual;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EmpresaIndividualViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public EmpresaIndividualViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is empresa individual fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
