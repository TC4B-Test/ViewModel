package com.tc4b.jmm.testeviewmodel.ui.main;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import com.tc4b.jmm.testeviewmodel.R;

import java.util.ArrayList;
import java.util.List;

class WrvListdapter extends RecyclerView.Adapter<WrvListdapter.WrvItemHolder> {
    private List<ItemLista> listaItems;
    ItemLista itemLista;
    private int selectItem;
    private SelectionTracker<ItemLista> selectionTracker;

    public WrvListdapter(List<ItemLista> listaItems) {
        this.listaItems = listaItems;
        selectItem = -1;
    }

    public void setListaItems(List<ItemLista> listaItems) {
        this.listaItems = listaItems;
    }

    public SelectionTracker<ItemLista> getSelectionTracker() {
        return selectionTracker;
    }

    public void setSelectionTracker(SelectionTracker<ItemLista> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    /**
     * Called when RecyclerView needs a new {@link WrvItemHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(WrvItemHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(WrvItemHolder, int)
     */
    @NonNull
    @Override
    public WrvItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_list_item_activated_by_jmm, parent, false);
        return new WrvItemHolder(itemView, this);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(WrvItemHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull WrvItemHolder holder, int position) {
//        holder.setItem(listaItems.get(position));
        itemLista = listaItems.get(position);
        holder.bind(listaItems.get(position), selectionTracker != null && selectionTracker.isSelected(itemLista));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return listaItems != null ? listaItems.size() : 0;
    }

    public int getSelectItem() {
        return selectItem;
    }

    public class WrvItemHolder extends RecyclerView.ViewHolder implements ViewHolderWithDetails<ItemLista>/*implements View.OnCreateContextMenuListener */ {
        private static final String TAG = "WrvItemHolder";
        private final WrvListdapter wrvListdapter;

        LinearLayout linearLayout;
        TextView textViewNome;
        TextView textViewGrupo;
        ItemLista itemValue;
        View.OnCreateContextMenuListener inBindContextMenu;

        @SuppressLint("ClickableViewAccessibility")
        public WrvItemHolder(@NonNull View itemView, WrvListdapter wrvListdapter) {
            super(itemView);
            this.wrvListdapter = wrvListdapter;
            textViewNome = itemView.findViewById(R.id.text1);
            textViewGrupo = itemView.findViewById(R.id.text2);
            linearLayout = itemView.findViewById(R.id.linearlayout_item);
            itemView.setOnTouchListener(new OnTouchListenerItem());
//            textViewNome.setLongClickable(true);// para poder aparecer o menu
//            textViewGrupo.setLongClickable(true);// para poder aparecer o menu
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    if(selectionTracker.getSelection().size()>1) {
                        menu.setHeaderTitle("All select items");
                    }else {
                        menu.setHeaderTitle(itemValue.getNome());
                    }
                    MenuItem myActionItem = menu.add("Remove(in holder)");
                    myActionItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                                                @Override
                                                                public boolean onMenuItemClick(MenuItem item) {
                                                                    Log.d(TAG, "onMenuItemClick: remove: " + itemValue.toString());
                                                                    List<Integer> listaRemove = new ArrayList<>();

                                                                    for (ItemLista itemLista : selectionTracker.getSelection()) {
                                                                        Log.d(TAG, "onMenuItemClick: Remove: "+itemLista.toString() );
                                                                        int index=listaItems.indexOf(itemLista);
                                                                        listaItems.remove(itemLista);
                                                                        wrvListdapter.notifyItemRemoved(index);
                                                                    }
                                                                    selectionTracker.clearSelection();
                                                                    return true;
                                                                }
                                                            }
                    );
                }

            });
        }

        public final void bind(ItemLista item, Boolean isActivated) {

            itemView.setActivated(isActivated);
            textViewNome.setText(item.getNome());
            textViewGrupo.setText(item.getGrupo());
            itemValue=itemLista;
        }

        @Override
        public ItemDetailsLookup.ItemDetails<ItemLista> getItemDetails() {
            Log.d(TAG, "getItemDetails() called");
            return new MyDetailsLookup.MyItemDetail(getAdapterPosition(), listaItems.get(getAdapterPosition()));
        }


        private class OnTouchListenerItem implements View.OnTouchListener {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                selectItem = getAdapterPosition();
                Log.e(TAG, "onTouch: " + listaItems.get(getAdapterPosition()));
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    linearLayout.setBackgroundColor(0x33000000);
                } else {
                    linearLayout.setBackgroundColor(0xffffffff);
                }
                Log.e("Acão", ": " + event.getAction());
                return false;
            }
        }
    }

    public interface ViewHolderWithDetails<T> {

        ItemDetailsLookup.ItemDetails<T> getItemDetails();

    }
}
