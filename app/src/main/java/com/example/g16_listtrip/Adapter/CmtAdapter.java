package com.example.g16_listtrip.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g16_listtrip.Activitys.PersonPage;
import com.example.g16_listtrip.DoiTuong.Comments;
import com.example.g16_listtrip.DoiTuong.USER;
import com.example.g16_listtrip.Interface.MyItemClickListener;
import com.example.g16_listtrip.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CmtAdapter extends RecyclerView.Adapter<CmtAdapter.CommentViewHolder> {
    private Context context;
    private ArrayList<Comments> comments;
    private long k = 0;

    public CmtAdapter(Context context, ArrayList<Comments> comments) {
        this.context = context;
        this.comments = comments;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcmt, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comments c = comments.get(position);
        if(c == null) {
            return;
        }
        holder.txtAccCmt.setText(c.getUser());
        holder.getAvatar(holder.txtAccCmt.getText().toString());
        holder.txtContenCmt.setText(c.getContentcmt());
        k = CalcHour(c.getDattimeCmt(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        if(k > 0 && k < 60)
        {
            holder.txtTimeCmt.setText("vài giây trước");
        } else if(k>=60 && k<3600)
        {
            holder.txtTimeCmt.setText((k/60)+" phút");
        } else if(k>=3600 && k<86399)
        {
            holder.txtTimeCmt.setText(k/3600+" giờ");
        } else if(k>=84000)
        {
            holder.txtTimeCmt.setText(k/84000+" ngày");
        }
        holder.setMyItemClickListener(new MyItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view, int postion, boolean isLongClick) {
                PopupMenu popupMenu = new PopupMenu(context, holder.txtAccCmt, Gravity.NO_GRAVITY);
                popupMenu.getMenuInflater().inflate(R.menu.menu_avatar_profile, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.mnviewAvartar:
                                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.mneditAvartar:

                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private ImageView imgavtCmt;
        private TextView txtAccCmt, txtContenCmt, txtTimeCmt, txtName;
        private MyItemClickListener myItemClickListener;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgavtCmt = (ImageView) itemView.findViewById(R.id.avatarcmt);
            txtAccCmt = (TextView) itemView.findViewById(R.id.tvAccCmt);
            txtName = (TextView) itemView.findViewById(R.id.tvNameCmt);
            txtContenCmt = (TextView) itemView.findViewById(R.id.tvCmt);
            txtTimeCmt = (TextView) itemView.findViewById(R.id.tvTimeCmt);
            itemView.setOnClickListener((View.OnClickListener) this);
            imgavtCmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent changetoProfile =  new Intent(context, PersonPage.class);
                    Bundle b = new Bundle();
                    b.putString("nameAcc",txtAccCmt.getText().toString());
                    changetoProfile.putExtras(b);
                    context.startActivity(changetoProfile);
                }
            });
        }
        public void setMyItemClickListener(MyItemClickListener myItemClickListener){
            this.myItemClickListener = myItemClickListener;
        }
        public void getAvatar(String s) {
            DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Profile").child(s);
            mdata.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    USER user = snapshot.getValue(USER.class);
                    txtName.setText(user.getsName());
                    imgavtCmt.setImageBitmap(StringToBitMap(user.getsImageA()));
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        @Override
        public void onClick(View v) {
            myItemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            myItemClickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }
    }
    public long CalcHour(String a, String b) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = new Date();

        Date d2 = new Date();

        try {

            d1 = format.parse(a);

            d2 = format.parse(b);

        } catch (ParseException e) {

        }

        // Get msec from each, and subtract.

        long diff = d2.getTime() - d1.getTime();

        long diffSeconds = diff / 1000;

        long diffMinutes = diff / (60 * 1000);

        long diffHours = diff / (60 * 60 * 1000);

        long diffDay = diff / (24*60*60*1000);

        return diffSeconds;

    }
    public String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return result;
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
