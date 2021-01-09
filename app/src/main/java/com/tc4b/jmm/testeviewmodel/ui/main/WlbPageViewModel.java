package com.tc4b.jmm.testeviewmodel.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class WlbPageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    private LiveData<List<String>> mText = Transformations.map(mIndex, new Function<Integer, List<String>>() {
        @Override
        public List<String> apply(Integer input) {
            return lista;
        }
    });

    private List<String> lista;

    public void setIndex(int index,List<String> lista) {
        this.lista = lista;
        mIndex.setValue(index);
    }

    public LiveData<List<String>> getList() {
        return mText;
    }
}