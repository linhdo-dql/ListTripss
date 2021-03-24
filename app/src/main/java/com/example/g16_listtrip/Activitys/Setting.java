package com.example.g16_listtrip.Activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.g16_listtrip.R;

public class Setting extends Fragment {
    private View rootView;
    Button btnset;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.setting, container,false);
        getsView();
        RandomTEXT();
        return rootView;

    }



    public void getsView() {

    }
    public void RandomTEXT() {
        Toast.makeText(getActivity(), "a", Toast.LENGTH_SHORT).show();
    }
}
