package com.tc4b.jmm.testeviewmodel.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tc4b.jmm.testeviewmodel.R;
import com.tc4b.jmm.testeviewmodel.TabsWithListBoxActivity;
import com.tc4b.jmm.testeviewmodel.WrvTabsActivity;

public class MainFragment extends Fragment {

    public static final int MAX_NUM_TABS = 8;
//    private MainViewModel mViewModel;
//    private Integer contador=0;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        Button buttonToListViews = rootView.findViewById(R.id.button2);
        Button buttonToRecycleView = rootView.findViewById(R.id.button);
        TextView mensagem = rootView.findViewById(R.id.message);
        SeekBar numTabs = rootView.findViewById(R.id.seekBar);

        numTabs.setMax(MAX_NUM_TABS);

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        numTabs.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.contador=progress+2;
                mensagem.setText(viewModel.contador.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
// Com List View's
        buttonToListViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabsWithListBoxActivity.class);
                intent.putExtra(TabsWithListBoxActivity.ARG1, viewModel.contador);
                startActivityForResult(intent, 1);
            }
        });
// Com Recycle View's
        buttonToRecycleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WrvTabsActivity.class);
                intent.putExtra(TabsWithListBoxActivity.ARG1, viewModel.contador);
                startActivityForResult(intent, 1);
            }
        });

//        mensagem.setText(viewModel.contador.toString());
        numTabs.setProgress(viewModel.contador);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

}