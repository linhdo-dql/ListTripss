package com.example.g16_listtrip.Activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.g16_listtrip.Adapter.Adapter_ListSchedule;
import com.example.g16_listtrip.DoiTuong.Schedule;
import com.example.g16_listtrip.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Schedule1 extends Fragment {
    private View rootView;
    private ListView ls;
    private Button btntest;
    private Adapter_ListSchedule adapterListSchedule;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.schedule1, container, false);
        initView();
        adapterListSchedule = new Adapter_ListSchedule(getActivity(), R.layout.item_schedule, getFBaseScheduleNow());
        ls.setAdapter(adapterListSchedule);
        return rootView;
    }
    public void initView()
    {
        ls = rootView.findViewById(R.id.listScheduleNow);
    }
    public ArrayList<Schedule> getFBaseScheduleNow() {
        final ArrayList<Schedule> a = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Trip").child(MainActivity.nameAcc);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                    long i = CalcHour(String.valueOf(snapshot1.child("timeIntend").getValue()), new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) );
                    if(i>=0 && i<= Integer.parseInt(snapshot1.child("time").getValue().toString())){
                        databaseReference.child(snapshot1.getKey()).child("Schedule").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                for (DataSnapshot d: snapshot2.getChildren())
                                {
                                    Schedule sc = d.getValue(Schedule.class);
                                    a.add(sc);
                                }
                                adapterListSchedule.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return a;
    }
    public long CalcHour(String a, String b) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = new Date();

        Date d2 = new Date();

        try {

            d1 = format.parse(a);

            d2 = format.parse(b);

        } catch (ParseException e) {

        }

        // Get msec from each, and subtract.

        long diff = d2.getTime() - d1.getTime();

        long diffDay = diff / (24*60*60*1000);

        return diffDay;
    }
}
