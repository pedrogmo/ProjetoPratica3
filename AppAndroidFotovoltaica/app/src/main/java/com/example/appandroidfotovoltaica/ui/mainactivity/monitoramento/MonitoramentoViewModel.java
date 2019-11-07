package com.example.appandroidfotovoltaica.ui.mainactivity.monitoramento;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonitoramentoViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public MonitoramentoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is monitoramento fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
