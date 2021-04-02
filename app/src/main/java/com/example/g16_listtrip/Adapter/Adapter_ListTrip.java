package com.example.g16_listtrip.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.g16_listtrip.Activitys.MainActivity;
import com.example.g16_listtrip.DoiTuong.Schedule;
import com.example.g16_listtrip.DoiTuong.Trip;
import com.example.g16_listtrip.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_ListTrip extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Trip> lisst;
    ArrayList<Schedule> schedules = new ArrayList<>();
    public Adapter_ListTrip(Context context, int layout, ArrayList<Trip> lisst) {
        this.context = context;
        this.layout = layout;
        this.lisst = lisst;
    }

    @Override
    public int getCount() {
        return lisst.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.itemtrip, parent, false);
        TextView textLoca = convertView.findViewById(R.id.txtNameTrip);
        TextView textTime = convertView.findViewById(R.id.txtTime);
        TextView textNumber = convertView.findViewById(R.id.txtAmout);
        TextView textCost = convertView.findViewById(R.id.txtCost);
        TextView textTimeAdd = convertView.findViewById(R.id.txtTimeAdd);
        TextView txtAddS = convertView.findViewById(R.id.txtAddS);
        ImageView imageView = convertView.findViewById(R.id.imagetrip);

        textLoca.setText(lisst.get(position).getLocation());
        textTime.setText(lisst.get(position).getTime());
        textNumber.setText(""+lisst.get(position).getAmountP());
        textCost.setText(""+lisst.get(position).getCost());
        textTimeAdd.setText(lisst.get(position).getTimeAdd());
        txtAddS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogShedule(position);
            }
        });
        return convertView;
    }
    public void showDialogShedule(int pos) {
        Dialog dia = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dia.setContentView(R.layout.dialo_schedule);
        dia.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        EditText edtToPlace = dia.findViewById(R.id.edtToPlace);
        EditText edtToTime = dia.findViewById(R.id.edtTimeTo);
        EditText edtVehicle = dia.findViewById(R.id.edtVehicle);
        Button btnAddS = dia.findViewById(R.id.btnAddSe);
        EditText edtNote = dia.findViewById(R.id.edtNote);
        ListView listSchedule = dia.findViewById(R.id.listS);
        Button btnCreateS = dia.findViewById(R.id.btnCreateS);
        Adapter_ListSchedule scheduleArrayAdapter = new Adapter_ListSchedule(context, R.layout.item_schedule, schedules);
        listSchedule.setAdapter(scheduleArrayAdapter);
        scheduleArrayAdapter.notifyDataSetChanged();
        btnCreateS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Trip").child(MainActivity.nameAcc);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot s: snapshot.getChildren()) {
                              if(s.child("timeAdd").getValue().equals(lisst.get(pos).getTimeAdd()))
                              {
                                  databaseReference.child(s.getKey()).child("Schedule").setValue(schedules);
                              }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        btnAddS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedules.add(new Schedule(edtToTime.getText().toString(), edtToPlace.getText().toString(), edtVehicle.getText().toString(),edtNote.getText().toString()));
                scheduleArrayAdapter.notifyDataSetChanged();
            }
        });
                dia.show();
    }
}
