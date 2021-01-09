package com.tc4b.jmm.testeviewmodel.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class WlbPagerTabsAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final int numeroTabs;

    public WlbPagerTabsAdapter(Context context, FragmentManager fm, int numeroTabs) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numeroTabs=numeroTabs;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a WlbPlaceholderTabFragment (defined as a static inner class below).
        return WlbPlaceholderTabFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "TAB "+(position+1);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return numeroTabs;
    }

    public void addItem() {
    }
}