package com.tc4b.jmm.testeviewmodel.ui.main;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

class MyDetailsLookup extends ItemDetailsLookup<ItemLista> {
    private static final String TAG = "MyDetailsLookup";
    RecyclerView  recycleView;

    public MyDetailsLookup(RecyclerView recycleView) {
        this.recycleView = recycleView;
    }

    /**
     * @param e
     * @return the ItemDetails for the item under the event, or null.
     */
    @Nullable
    @Override
    public ItemDetails<ItemLista> getItemDetails(@NonNull MotionEvent e) {
        if(e.getAction() == MotionEvent.ACTION_DOWN){
            Log.d(TAG+":getItemDetails", "MotionEvent: ACTION_DOWN");
        }else
        if(e.getAction() == MotionEvent.ACTION_UP){
            Log.e(TAG+":getItemDetails", "MotionEvent: ACTION_UP");
        }else
        if(e.getAction() == MotionEvent.ACTION_CANCEL){
            Log.e(TAG+":getItemDetails", "MotionEvent: ACTION_CANCEL");
        }else
        if(e.getAction() == MotionEvent.ACTION_OUTSIDE){
            Log.e(TAG+":getItemDetails", "MotionEvent: ACTION_OUTSIDE");
        }else {
            Log.e(TAG + ":getItemDetails", "MotionEvent: " + e.getAction());
        }

        View view = recycleView.findChildViewUnder(e.getX(), e.getY());
        if(view != null){
            RecyclerView.ViewHolder viewHolder =  recycleView.getChildViewHolder(view);
            if(viewHolder instanceof WrvListdapter.WrvItemHolder) {
                ItemDetails<ItemLista> listaItemDetails = ((WrvListdapter.WrvItemHolder) viewHolder).getItemDetails();
                Log.d(TAG, "getItemDetails: " + listaItemDetails.getPosition()+" Key: "+listaItemDetails.getSelectionKey());
                return listaItemDetails;
            }
        }
        return null;
    }

    public static class MyItemDetail extends ItemDetails<ItemLista>{
        private final ItemLista key;
        private final int adapterPosition;

        public MyItemDetail(int adapterPosition, ItemLista key) {
            this.adapterPosition = adapterPosition;
            this.key = key;
        }

        /**
         * Returns the adapter position of the item. See
         * {@link RecyclerView.ViewHolder#getAdapterPosition() ViewHolder.getAdapterPosition}
         *
         * @return the position of an item.
         */
        @Override
        public int getPosition() {
            return adapterPosition;
        }

        /**
         * @return the selection key of an item.
         */
        @Nullable
        @Override
        public ItemLista getSelectionKey() {
            return key;
        }

        @Override
        public String toString() {
            return "MyItemDetail{" +
                    "key=" + key +
                    ", adapterPosition=" + adapterPosition +
                    '}';
        }
    }
}
