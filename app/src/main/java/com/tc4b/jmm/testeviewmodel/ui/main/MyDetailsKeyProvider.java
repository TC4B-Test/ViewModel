package com.tc4b.jmm.testeviewmodel.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class MyDetailsKeyProvider extends ItemKeyProvider<ItemLista> {
    private final List<ItemLista> itemListas;

    public MyDetailsKeyProvider(int scope, List<ItemLista> itemListas) {
        super(scope);
        this.itemListas = itemListas;
    }

    /**
     * @param position
     * @return The selection key at the given adapter position, or null.
     */
    @Nullable
    @Override
    public ItemLista getKey(int position) {
        if(itemListas!=null && itemListas.size()>position)
            return itemListas.get(position);
        return null;
    }

    /**
     * @param key
     * @return the position corresponding to the selection key, or RecyclerView.NO_POSITION.
     */
    @Override
    public int getPosition(@NonNull ItemLista key) {
        return itemListas.indexOf(key);
    }
}
