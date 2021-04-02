package com.example.g16_listtrip.Activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.g16_listtrip.Adapter.Adapter_ListTrip;
import com.example.g16_listtrip.DoiTuong.Trip;
import com.example.g16_listtrip.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Schedule2 extends Fragment {
    private View rootView;
    private ListView listTripss;
    private Adapter_ListTrip adaptertrip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.schedule2, container, false);
        initView();
        adaptertrip = new Adapter_ListTrip(getActivity(), R.layout.itemtrip, getFbaseTrip());
        listTripss.setAdapter(adaptertrip);
        return rootView;

    }
    public void initView() {
        listTripss = (ListView) rootView.findViewById(R.id.listTripss);
    }
    public ArrayList<Trip> getFbaseTrip() {
        ArrayList<Trip> arrayList2 = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Trip").child(MainActivity.nameAcc);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        arrayList2.add(trip);
                    }

                adaptertrip.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return arrayList2;
    }
}
