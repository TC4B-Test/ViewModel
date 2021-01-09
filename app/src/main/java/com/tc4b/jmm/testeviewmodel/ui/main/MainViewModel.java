package com.tc4b.jmm.testeviewmodel.ui.main;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public MainViewModel() {
        contador =0;
    }

    // TODO: Implement the ViewModel
    public Integer contador;

    public void addNumber(){
        contador++;
    }
}