package com.tc4b.jmm.testeviewmodel.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tc4b.jmm.testeviewmodel.ui.main.ItemLista.ListOfItemList;

import java.util.ArrayList;
import java.util.List;

public class WrvPageViewModel extends ViewModel {
    private final MutableLiveData<List<ListOfItemList>> mList;
    private LoadListData loadListData;

    public WrvPageViewModel() {
        mList = new MutableLiveData<>();
        List<ListOfItemList> ListaDeLista = new ArrayList<>();
        mList.setValue(ListaDeLista);

    }

    public void setLoadListData(LoadListData loadListData) {
        this.loadListData = loadListData;
    }

    public List<ItemLista> getList(Integer indice) {
        if (indice < 0) {
            return null;
        } else {
            int i;
            for (i = mList.getValue().size(); i-- > 0; ) {
                if (mList.getValue().get(i).index == indice) {
                    return mList.getValue().get(i).listaList;
                }
            }
            ListOfItemList listOfItemList = new ListOfItemList(indice, loadListData.getList());
            mList.getValue().add(listOfItemList);

            return listOfItemList.listaList;
        }
    }

    public MutableLiveData<List<ListOfItemList>> getList() {
        return mList;
    }
}