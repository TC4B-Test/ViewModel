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
    //    private LiveData<List<ItemLista>> lList ;
//    private List<ItemLista> lista;

//    public LiveData<List<ItemLista>> getList(Integer indice) {
//
//
//        if (indice < 0) {
//            return null;
//        }else{
//            int i;
//            for (i = listaIndices.size(); i-- > 0; ) {
//                if (listaIndices.get(i).equals(indice)) {
//                    break;
//                }
//            }
//            if (i < 0) {
//                MutableLiveData<List<ItemLista>> mList = new MutableLiveData<List<ItemLista>>();
//                loadList(mList);
//                i = listaIndices.size();
//                lmList.add(mList);
//                listaIndices.add(indice);
//            }
//            return lmList.get(i);
//        }
//    }
//    public List<ItemLista> getList(Integer indice) {
//
//        if (indice < 0) {
//            return null;
//        } else {
//            int i;
//            for (i = listaIndices.size(); i-- > 0; ) {
//                if (listaIndices.get(i).equals(indice)) {
//                    break;
//                }
//            }
//            if (i < 0) {
//                lmList.getValue().add(loadListData.getList());
//                i = listaIndices.size();
//                listaIndices.add(indice);
//            }
//            return lmList.getValue().get(i);
//        }
//    }

//    private void loadList(MutableLiveData<List<ItemLista>> mList) {
//        mList.setValue();
//    }
}