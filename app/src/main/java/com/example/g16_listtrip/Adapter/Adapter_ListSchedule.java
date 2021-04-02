package com.example.g16_listtrip.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.g16_listtrip.DoiTuong.Schedule;
import com.example.g16_listtrip.R;

import java.util.ArrayList;

public class Adapter_ListSchedule extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Schedule> lisstS;

    public Adapter_ListSchedule(Context context, int layout, ArrayList<Schedule> lisstS) {
        this.context = context;
        this.layout = layout;
        this.lisstS = lisstS;
    }

    @Override
    public int getCount() {
        return lisstS.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_schedule,parent,false);
        TextView txtPlace = convertView.findViewById(R.id.txtPlace);
        TextView txtNote = convertView.findViewById(R.id.txtNote);
        TextView txtVehicle = convertView.findViewById(R.id.txtVehicle);
        TextView txtTime = convertView.findViewById(R.id.txtTime);
        txtPlace.setText(lisstS.get(position).getPlaceTo());
        txtNote.setText(lisstS.get(position).getNote());
        txtVehicle.setText(lisstS.get(position).getVehicle());
        txtTime.setText(lisstS.get(position).getTimeTo());
        return convertView;
    }
}
