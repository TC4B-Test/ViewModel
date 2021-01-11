package com.tc4b.jmm.testeviewmodel.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.OnContextClickListener;
import androidx.recyclerview.selection.OnDragInitiatedListener;
import androidx.recyclerview.selection.OnItemActivatedListener;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tc4b.jmm.testeviewmodel.R;
import com.tc4b.jmm.testeviewmodel.ui.main.ItemLista.ListOfItemList;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;


/**
 * A placeholder fragment containing a simple view.
 */
public class WrvPlaceholderTabFragment extends Fragment {
    private static final String TAG = "WrvPlaceholderTabFragme";

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_ORDENCRIACAO = "ordenCriacaoNewInstance";
    private static final int MAX_NUM_ITEMS = 20;
    private static int ordenCriacaoNewInstance = 0;

    private WrvPageViewModel pageViewModel;
    private WrvListdapter wrvListdapter;
    private int posicaoAtualMenu = -1;
    private RecyclerView recycleView;
    //    private ListView selectListView;
    private List<ItemLista> listaDados;

    int index = -1;
    int ordenCriaco = -1;
    private ItemLista selectedItem;

    public static WrvPlaceholderTabFragment newInstance(int index) {
        WrvPlaceholderTabFragment fragment = new WrvPlaceholderTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putInt(ARG_ORDENCRIACAO, ordenCriacaoNewInstance++);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(getActivity()).get(WrvPageViewModel.class);


        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            ordenCriaco = getArguments().getInt(ARG_ORDENCRIACAO);
        }
        pageViewModel.setLoadListData(new LoadListData() {
            @Override
            public List<ItemLista> getList() {
                List<ItemLista> aLista = new ArrayList<>();
                int nLinhas = (int) (Math.random() * MAX_NUM_ITEMS);
                for (int i = 0; i < nLinhas; i++) {
                    aLista.add(new ItemLista(ordenCriaco * MAX_NUM_ITEMS + i + 1,
                            "Nome: " + i + " : n. Ordem: " + ordenCriaco, "Grupo: " + index));
                }

                return aLista;
            }
        });
    }

    static boolean bObserve = true;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tabs_recicleview, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        textView.setText("N. Tabs: " + (getArguments().getInt(ARG_SECTION_NUMBER) + 1));
        recycleView = root.findViewById(R.id.recycleview_in_tab);
        recycleView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        registerForContextMenu(recycleView);

        listaDados = pageViewModel.getList(index);
        wrvListdapter = new WrvListdapter(listaDados);
        recycleView.setAdapter(wrvListdapter);

        SelectionTracker<ItemLista> tracker = new SelectionTracker.Builder<>(
                "my-selection-id",
                recycleView,
                new MyDetailsKeyProvider(1, listaDados),  // 'StableIdKeyProvider extends ItemKeyProvider<Long>'
                new MyDetailsLookup(recycleView),
                StorageStrategy.createParcelableStorage(ItemLista.class))
                    .withOnItemActivatedListener(new OnItemActivatedListener<ItemLista>() {
                    @Override
                    public boolean onItemActivated(@NonNull ItemDetailsLookup.ItemDetails<ItemLista> item, @NonNull MotionEvent e) {
                        Log.e("onItemActivated", "Selected ItemId: " + item.toString() + " MotionEvent: " + e);
                        return true;
                    }
                })
                    .withOnDragInitiatedListener(new OnDragInitiatedListener() {
                    @Override
                    public boolean onDragInitiated(@NonNull MotionEvent e) {
                        Log.e("onDragInitiated", e.toString());
                        return true;
                    }
                })
                    .withOnContextClickListener(new OnContextClickListener() {
                    @Override
                    public boolean onContextClick(@NonNull MotionEvent e) {
                        Log.e(TAG, "onContextClick: MotionEvent:" + e);
                        return false;
                    }
                })
                    .withSelectionPredicate(new SelectionTracker.SelectionPredicate<ItemLista>() {
                                            @Override
                                            public boolean canSetStateForKey(@NonNull ItemLista key, boolean nextState) {
                                                selectedItem = key;
                                                Log.e(TAG, "canSetStateForKey: "+key.toString()+"$nextState:" + nextState);
                                                return true;
                                            }

                                            @Override
                                            public boolean canSetStateAtPosition(int position, boolean nextState) {
                                                Log.e(TAG, "canSetStateAtPosition: posicao: " + position);
                                                return false;
                                            }

                                            @Override
                                            public boolean canSelectMultiple() {
                                                Log.e(TAG, "canSelectMultiple: ");
                                                return true;
                                            }
                                        })
                    .build();
        wrvListdapter.setSelectionTracker(tracker);
        if(bObserve && getActivity()!=null) {
            // ver 01
//            bObserve=false;
            pageViewModel.getList().observe(getActivity(), new Observer<List<ListOfItemList>>() {
                /**
                 * Called when the data is changed.
                 *
                 * @param listOfItemsList The new data
                 */
                @Override
                public void onChanged(List<ListOfItemList> listOfItemsList) {
                    for (ListOfItemList listOfItemList : listOfItemsList) {
                        if (listOfItemList.index == index) {
                            wrvListdapter.setListaItems(listOfItemList.listaList);
                        }
                    }
                }

            });
        }
        return root;
    }

    /**
     * Called when a context menu for the {@code view} is about to be shown.
     * Unlike {@link #onCreateOptionsMenu}, this will be called every
     * time the context menu is about to be shown and should be populated for
     * the view (or item inside the view for {@link AdapterView} subclasses,
     * this can be found in the {@code menuInfo})).
     * <p>
     * Use {@link #onContextItemSelected(MenuItem)} to know when an
     * item has been selected.
     * <p>
     * It is not safe to hold onto the context menu after this method returns.
     * {@inheritDoc}
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        //  super.onCreateContextMenu(menu, v, menuInfo);
        int viewID = v.getId();
//        if (viewID == R.id.text1) {
        if (viewID == R.id.recycleview_in_tab) {
//            selectListView = (ListView) v;
            AdapterView.AdapterContextMenuInfo oMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
//            posicaoAtualMenu=oMenuInfo.position;
            String item;
            if (selectedItem != null) {
                item = selectedItem.getNome();
            } else {
                item = "Menu em RcycleView";
            }
            Log.e(TAG, "onCreateContextMenu: -------------- Criação do menu");
//            menu.setHeaderTitle(item);
            menu.add(Menu.NONE, 1, Menu.NONE, "MENU_EDITAR");
            menu.add(Menu.NONE, 2, Menu.NONE, "MENU_ELIMINAR");

        }
    }

    /**
     * This hook is called whenever an item in a context menu is selected. The
     * default implementation simply returns false to have the normal processing
     * happen (calling the item's Runnable or sending a message to its Handler
     * as appropriate). You can use this method for any items for which you
     * would like to do processing without those other facilities.
     * <p>
     * Use {@link MenuItem#getMenuInfo()} to get extra information set by the
     * View that added this menu item.
     * <p>
     * Derived classes should call through to the base class for it to perform
     * the default menu handling.
     *
     * @param item The context menu item that was selected.
     * @return boolean Return false to allow normal context menu processing to
     * proceed, true to consume it here.
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        List<ItemLista> aLista = pageViewModel.getList(index);
        if (item.getItemId() == 1) {// editar

        } else if (item.getItemId() == 2) {/* eliminar */

            if (wrvListdapter != null) {
                Log.e("onContextItemSelected", "wrvListdapter.getSelectItem(): " + wrvListdapter.getSelectItem());
            }
            if (selectedItem != null) {
                Log.e("onContextItemSelected", "selectItem: " + selectedItem.toString());
            }


//            if(selectListView!=null){
//                Log.e("onContextItemSelected","SelectListView " + selectListView);
//                aLista.remove(posicaoAtualMenu);
//                //selectListView.getAdapter().notify();
//                selectListView=null;
//                return true;
//            }else{
//                Log.e("onContextItemSelected","SelectListView NULL " + selectListView);
//            }
            Log.e("onContextItemSelected", "Lista Size: " + aLista.size());
//            for(ItemLista linha : aLista){
//                Log.e("onContextItemSelected", linha.toString());
//            }
            return false;
        } else {
            Toast.makeText(getContext(), "Não deveria estar aqui!!!", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

}