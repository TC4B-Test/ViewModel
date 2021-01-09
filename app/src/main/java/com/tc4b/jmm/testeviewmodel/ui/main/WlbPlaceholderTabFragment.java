package com.tc4b.jmm.testeviewmodel.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tc4b.jmm.testeviewmodel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class WlbPlaceholderTabFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_ORDENCRIACAO = "ordenCriacaoNewInstance";
    private static int ordenCriacaoNewInstance =0;

    private WlbPageViewModel pageViewModel;
    private int posicaoAtualMenu=-1;
    private ListView selectListView;

    public static WlbPlaceholderTabFragment newInstance(int index) {
        WlbPlaceholderTabFragment fragment = new WlbPlaceholderTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putInt(ARG_ORDENCRIACAO, ++ordenCriacaoNewInstance);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(WlbPageViewModel.class);

        int index = -1;
        int ordenCriaco = -1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            ordenCriaco = getArguments().getInt(ARG_ORDENCRIACAO);
        }
        List<String> listaDados = new ArrayList<>();
        int nLinhas = (int) (Math.random()*20+2);
        for(int i =0 ; i < nLinhas ; i++){
            listaDados.add("I:"+index+" O:"+ordenCriaco+"N:"+i);
        }
        pageViewModel.setIndex(index, listaDados);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tabs_listview, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        textView.setText("Numero de Tabs: "+getArguments().getInt(ARG_SECTION_NUMBER));
        final ListView listView = root.findViewById(R.id.listview_in_tab);
//        registerForContextMenu(listView);
        pageViewModel.getList().observe(this, new Observer<List<String>>() {
            /**
             * Called when the data is changed.
             *
             * @param listaItems The new data
             */
            @Override
            public void onChanged(List<String> listaItems) {
                ArrayAdapter<String> adLista = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listaItems);
                listView.setAdapter(adLista);
            }

        });
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
        if (v.getId() == R.id.listview_in_tab) {
            selectListView = (ListView) v;
            AdapterView.AdapterContextMenuInfo oMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicaoAtualMenu=oMenuInfo.position;
            String item = (String) selectListView.getItemAtPosition(posicaoAtualMenu);

            menu.setHeaderTitle(item);
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
        List<String> aLista = pageViewModel.getList().getValue();
        if(item.getItemId()==1) {// editar

        }
        else if(item.getItemId()==2) {// eliminar

            if(selectListView!=null){
                Log.e("onContextItemSelected","SelectListView " + selectListView);
                aLista.remove(posicaoAtualMenu);
                //selectListView.getAdapter().notify();
                selectListView=null;
                return true;
            }else{
                Log.e("onContextItemSelected","SelectListView NULL " + selectListView);
            }
            Log.e("onContextItemSelected","Lista Size: "+aLista.size());
            for(String str : aLista){
                Log.e("onContextItemSelected", str);
            }
            return false;
        }
        else{
            Toast.makeText(getContext(),"Não deveria estar aqui!!!",Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }
}