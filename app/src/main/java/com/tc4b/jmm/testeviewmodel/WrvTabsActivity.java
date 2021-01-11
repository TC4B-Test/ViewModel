package com.tc4b.jmm.testeviewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tc4b.jmm.testeviewmodel.ui.main.ItemLista;
import com.tc4b.jmm.testeviewmodel.ui.main.WrvPageViewModel;
import com.tc4b.jmm.testeviewmodel.ui.main.WrvPagerTabsAdapter;

import java.util.List;

public class WrvTabsActivity extends AppCompatActivity {
    private static final String TAG = "WrvTabsActivity";

    private int tabPosition = -1;
    static int cont = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
        int nTabs = 10;
        tabPosition = 0;

        setContentView(R.layout.activity_tabs_viewpager2);
        WrvPagerTabsAdapter sectionsPagerAdapter = new WrvPagerTabsAdapter(this,  nTabs);

        ViewPager2 viewPager = findViewById(R.id.view_pager2);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabs, viewPager, (tab, position) -> {
            tab.setText("Tab: "+(position+1));
        });
        tabLayoutMediator.attach();



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            /**
             * Called when a tab enters the selected state.
             *
             * @param tab The tab that was selected
             */
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                Toast.makeText(getApplicationContext(),"onTabSelected: "+tab.getPosition(), Toast.LENGTH_SHORT).show();
            }

            /**
             * Called when a tab exits the selected state.
             *
             * @param tab The tab that was unselected
             */
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            /**
             * Called when a tab that is already selected is chosen again by the user. Some applications may
             * use this action to return to the top level of a category.
             *
             * @param tab The tab that was reselected.
             */
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Toast.makeText(getApplicationContext(),"onTabReselected: "+ tabPosition, Toast.LENGTH_SHORT).show();
            }
        });


        //        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sectionsPagerAdapter.addListItem();
                if(tabPosition>=0){
                    WrvPageViewModel pageViewModel = new ViewModelProvider(WrvTabsActivity.this).get(WrvPageViewModel.class);
                    List<ItemLista>  listaDados = pageViewModel.getList(tabPosition);
                    listaDados.add(new ItemLista(++cont,"NOME " + cont, "GRUPO " + cont));
                }
                viewPager.getAdapter().notifyItemChanged(tabPosition);
            }
        });

    }
}