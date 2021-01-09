package com.tc4b.jmm.testeviewmodel.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.tc4b.jmm.testeviewmodel.WrvTabsActivity;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class WrvPagerTabsAdapter extends FragmentStateAdapter {
    private int numeroTabs;// 'FragmentStateAdapter extends RecyclerView.Adapter'

    public WrvPagerTabsAdapter(WrvTabsActivity wrvTabsActivity, int numeroTabs) {
        super(wrvTabsActivity);
        this.numeroTabs= numeroTabs;
    }

    public void addListItem() {
    }

    /**
     * Provide a new Fragment associated with the specified position.
     * <p>
     * The adapter will be responsible for the Fragment lifecycle:
     * <ul>
     *     <li>The Fragment will be used to display an item.</li>
     *     <li>The Fragment will be destroyed when it gets too far from the viewport, and its state
     *     will be saved. When the item is close to the viewport again, a new Fragment will be
     *     requested, and a previously saved state will be used to initialize it.
     * </ul>
     *
     * @param position
     * @see ViewPager2#setOffscreenPageLimit
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return WrvPlaceholderTabFragment.newInstance(position);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return numeroTabs;
    }
}