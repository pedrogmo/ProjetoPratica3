package com.example.appandroidfotovoltaica.ui.mainactivity.propostas.visualizarproposta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VisualizarPropostaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VisualizarPropostaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is propostas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}