package com.example.g16_listtrip.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g16_listtrip.DoiTuong.Stories;
import com.example.g16_listtrip.R;

import java.util.ArrayList;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {
    Context context;
    ArrayList<Stories> listStories;

    public StoriesAdapter(Context context, ArrayList<Stories> listStories) {
        this.context = context;
        this.listStories  = listStories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemstory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stories stories = listStories.get(position);
        holder.txtTk.setText(stories.getAccS());
        holder.txtdate.setText(stories.getTimeS());
        holder.imageS.setImageBitmap(StringToBitMap(stories.imgS));
    }

    @Override
    public int getItemCount() {
        return listStories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageS;
        TextView txtTk, txtdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageS =  itemView.findViewById(R.id.imagestr);
            txtTk = itemView.findViewById(R.id.txttaikhoan);
            txtdate = itemView.findViewById(R.id.txtngaygio);
        }
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
