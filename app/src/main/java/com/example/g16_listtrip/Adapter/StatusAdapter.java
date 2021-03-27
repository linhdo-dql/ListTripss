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

import com.example.g16_listtrip.Activitys.MainActivity;
import com.example.g16_listtrip.DoiTuong.Like;
import com.example.g16_listtrip.DoiTuong.Status;
import com.example.g16_listtrip.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StatusAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<Status> status;
    int slpress = 0, like = 0;
    TextView txttimeStt, txtUsertStt, txtLike;
    ImageView heartlike;

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
        txtUsertStt = (TextView) convertView.findViewById(R.id.tvUserMaster);
        txtUsertStt.setText(status.get(position).usermaster);
        txttimeStt = (TextView) convertView.findViewById(R.id.timeStt);
        CalcHour(String.valueOf(status.get(position).datetimeStt), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        TextView txtlocationStt = (TextView) convertView.findViewById(R.id.tvLocationSttItem);
        txtlocationStt.setText(status.get(position).locationStt);
        TextView txtcontentStt = (TextView) convertView.findViewById(R.id.contenSttItems);
        txtcontentStt.setText(status.get(position).contentStt);
        ImageView imgStt = (ImageView) convertView.findViewById(R.id.imageSttItem);
        imgStt.setImageBitmap(bs.StringToBitMap(status.get(position).bitImgStt));
        txtLike = (TextView) convertView.findViewById(R.id.txtLike);
        heartlike = (ImageView) convertView.findViewById(R.id.heartLike);
        CheckLiked(position);
        LinearLayout btnLike = (LinearLayout) convertView.findViewById(R.id.bLike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                slpress++;
                if(slpress==1) {
                    heartlike.setBackgroundResource(R.drawable.heartok);
                    txtLike.setText("");
                    AttackLike(position);
                }
                else
                {
                    slpress = 0;
                    heartlike.setBackgroundResource(R.drawable.heart);
                    txtLike.setText("Yêu thích");
                    AttackDiskLike(position);
                }
            }
        });
        LinearLayout btnComment = (LinearLayout) convertView.findViewById(R.id.bComment);
        LinearLayout btnShare = (LinearLayout) convertView.findViewById(R.id.bShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                } catch (Exception e) {
                    Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        return convertView;
    }

    public void CalcHour(String a, String b) {
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

        if (diffSeconds < 10) {
            txttimeStt.setText("Vài giây trước");
        } else if (diffMinutes < 1 && diffSeconds > 60) {
            txttimeStt.setText("Gần 1 phút trước");
        } else if (diffMinutes >= 1 && diffMinutes < 60) {
            txttimeStt.setText(diffMinutes + " phút");
        } else if (diffMinutes >= 60 && diffHours >= 1) {
            txttimeStt.setText(diffHours + " giờ");
        }

    }

    public void AttackLike(int pos) {
        DatabaseReference databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Status");
        Query mDataRef = FirebaseDatabase.getInstance().getReference("Status").orderByChild("usermaster").equalTo(txtUsertStt.getText().toString());
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    if (s.child("datetimeStt").getValue().toString().equals(status.get(pos).datetimeStt)) {
                        Like likes = new Like(1, MainActivity.nameAcc);
                        databaseReference.child(s.getKey()).child("like").push().setValue(likes);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void CheckLiked(int pos) {
        Query mQuery = FirebaseDatabase.getInstance().getReference("Status").orderByChild("usermaster").equalTo(txtUsertStt.getText().toString());
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    if (s.child("datetimeStt").getValue().toString().equals(status.get(pos).datetimeStt)) {
                       Query mQuery2 = FirebaseDatabase.getInstance().getReference("Status").child(s.getKey()).child("like").orderByChild("userLike").equalTo(MainActivity.nameAcc);
                       mQuery2.addValueEventListener(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                               if(snapshot.exists())
                               {
                                    heartlike.setBackgroundResource(R.drawable.heartok);
                                    txtLike.setText("");
                               }
                               else
                               {
                                   heartlike.setBackgroundResource(R.drawable.heart);
                                   txtLike.setText("Yêu thích");
                               }
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
    }
    public void AttackDiskLike(int pos) {
        Query mQuery = FirebaseDatabase.getInstance().getReference("Status").orderByChild("usermaster").equalTo(txtUsertStt.getText().toString());
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    if (s.child("datetimeStt").getValue().toString().equals(status.get(pos).datetimeStt)) {
                        DatabaseReference mRef = FirebaseDatabase.getInstance()
                                .getReference("Status")
                                .child(s.getKey())
                                .child("like");
                        Query mQuery2 = FirebaseDatabase.getInstance().getReference("Status").child(s.getKey()).child("like").orderByChild("userLike").equalTo(MainActivity.nameAcc);
                        mQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists())
                                {
                                   for(DataSnapshot nap: snapshot.getChildren())
                                   {
                                       mRef.removeValue();
                                   }
                                }
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
    }

}
