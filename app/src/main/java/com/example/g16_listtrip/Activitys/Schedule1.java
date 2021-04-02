package com.example.g16_listtrip.Activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Schedule1 extends Fragment {
    private View rootView;
    private ListView ls;
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
                    if(daysBetween2Dates(snapshot1.child("timeIntend").getValue().toString(), new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()))>= (long) snapshot1.child("time").getValue())
                    {
                        databaseReference.child(snapshot1.getKey()).child("Schedule").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                for(DataSnapshot dataSnapshot2 : snapshot2.getChildren())
                                {
                                    Schedule s = dataSnapshot2.getValue(Schedule.class);
                                    a.add(s);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    adapterListSchedule.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return a;
    }
    public long daysBetween2Dates(String s1, String s2) {
        // Định dạng thời gian
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        // Định nghĩa 2 mốc thời gian ban đầu
        Date date1 = Date.valueOf(s1);
        Date date2 = Date.valueOf(s2);

        c1.setTime(date1);
        c2.setTime(date2);

        // Công thức tính số ngày giữa 2 mốc thời gian:
        return (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);
    }
}
