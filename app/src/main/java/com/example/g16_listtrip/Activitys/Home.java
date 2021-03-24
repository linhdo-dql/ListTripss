package com.example.g16_listtrip.Activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g16_listtrip.Adapter.StoriesAdapter;
import com.example.g16_listtrip.DoiTuong.Stories;
import com.example.g16_listtrip.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends Fragment {
    private View rootView;
    ListView listStt;
    RecyclerView listStr;
    ArrayList<Stories> Arrstr  = new ArrayList<>();
    DatabaseReference mDataRef;
    StoriesAdapter storiesAdapter;
    ArrayAdapter<Stories> adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home, container,false);
        initView();
        adapter = new ArrayAdapter<Stories>(getContext(), android.R.layout.simple_list_item_1, getFBase());
        listStt.setAdapter(adapter);
        storiesAdapter=new StoriesAdapter(getActivity(), getFBase());
        listStr.setAdapter(storiesAdapter);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listStr.setLayoutManager(horizontalLayoutManagaer);
        storiesAdapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();

        return rootView;
    }
    public void initView() {
        listStr = (RecyclerView) rootView.findViewById(R.id.listStr);
        listStt = (ListView) rootView.findViewById(R.id.listStt);
    }
    public ArrayList<Stories> getFBase() {
        mDataRef = FirebaseDatabase.getInstance().getReference("Story");
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for( DataSnapshot snap : snapshot.getChildren())
                    {
                        Stories stories = snap.getValue(Stories.class);
                        Arrstr.add(stories);
                    }
                }
                storiesAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return Arrstr;
    }
}
