package com.example.g16_listtrip.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.g16_listtrip.DoiTuong.Status;
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

public class StatusAdapter extends BaseAdapter
{
    Context context;
    int layout;
    ArrayList<Status> status;
    int slpress = 0, like = 0;
    TextView txttimeStt;
    String time = "";
    public StatusAdapter(Context context, int layout, ArrayList<Status> status) {
        this.context = context;
        this.layout = layout;
        this.status = status;
    }




    @Override
    public int getCount() {
        return status.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        BitmapVsString bs = new BitmapVsString();
        ImageView imgAvatar = (ImageView) convertView.findViewById(R.id.avatarStt);
        imgAvatar.setBackgroundResource(R.drawable.anh1);
        TextView txtUsertStt = (TextView) convertView.findViewById(R.id.tvUserMaster);
        txtUsertStt.setText(status.get(position).usermaster);
        txttimeStt = (TextView) convertView.findViewById(R.id.timeStt);
        txttimeStt.setText(CalcHour(String.valueOf(status.get(position).datetimeStt), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()).toString()));
        TextView txtlocationStt = (TextView) convertView.findViewById(R.id.tvLocationSttItem);
        txtlocationStt.setText(status.get(position).locationStt);
        TextView txtcontentStt = (TextView) convertView.findViewById(R.id.contenSttItems);
        txtcontentStt.setText(status.get(position).contentStt);
        ImageView imgStt = (ImageView) convertView.findViewById(R.id.imageSttItem);
        imgStt.setImageBitmap(bs.StringToBitMap(status.get(position).bitImgStt));
        like = Integer.parseInt(String.valueOf(status.get(position).like));
        TextView txtlike = (TextView) convertView.findViewById(R.id.txtLike);
        ImageView heartlike = (ImageView) convertView.findViewById(R.id.heartLike);
        LinearLayout btnLike = (LinearLayout) convertView.findViewById(R.id.bLike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                slpress++;
                if(slpress%2!=0)
                {
                    like = like + 1;
                    txtlike.setText("("+like+")");
                    txtlike.setTextColor(R.color.pingLove);
                    heartlike.setBackgroundResource(R.drawable.heartok);
                    UpdateLike(position);
                }
                else
                {
                    like = 0;
                    txtlike.setText("Yêu thích");
                    txtlike.setTextColor(R.color.defaultC);
                    heartlike.setBackgroundResource(R.drawable.heart);
                }
            }
        });
        LinearLayout btnComment = (LinearLayout) convertView.findViewById(R.id.bComment);
        LinearLayout btnShare = (LinearLayout) convertView.findViewById(R.id.bShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {

                    }catch (Exception e)
                    {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

            }
        });
        return convertView;
    }

    public String CalcHour(String a, String b) {
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

        if(diffSeconds<10)
        {
            txttimeStt.setText("Vài giây trước");
        }
        else if(diffMinutes < 1 && diffSeconds > 60)
        {
            txttimeStt.setText("Gần 1 phút trước");
        }
        else if(diffMinutes >= 1 && diffMinutes < 60)
        {
            txttimeStt.setText(diffMinutes+" phút");
        }
        else if(diffMinutes>= 60 && diffHours >= 1)
        {
            txttimeStt.setText(diffHours+ " giờ");
        }
        return txttimeStt.getText().toString();
    }

        public void UpdateLike(int pos) {
        DatabaseReference mDataRef = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Status").orderByChild(status.get(pos).usermaster);
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(txttimeStt.getText().toString().equals(CalcHour(String.valueOf(status.get(pos).datetimeStt), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()).toString())))
                {
                    snapshot.getRef().child("like").setValue(Integer.parseInt(String.valueOf(status.get(pos).like)+like));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
