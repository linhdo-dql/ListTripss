package com.example.g16_listtrip.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g16_listtrip.Activitys.MainActivity;
import com.example.g16_listtrip.DoiTuong.Comments;
import com.example.g16_listtrip.DoiTuong.Like;
import com.example.g16_listtrip.DoiTuong.Status;
import com.example.g16_listtrip.DoiTuong.USER;
import com.example.g16_listtrip.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {
    private Context context;
    private ArrayList<Status> listS;
    private long z = 0;
    private String name, avatar;
    public StatusAdapter(Context context, ArrayList<Status> fBaseSTT) {
        this.context = context;
        this.listS = fBaseSTT;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemstt, parent, false);
        return new StatusViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
            Status s = listS.get(position);
            if(s == null) {
                return;
            }
            holder.position = position;
            holder.tvUser.setText(s.getUsermaster());
            holder.getAvatar(holder.tvUser.getText().toString());
            z = CalcHour(s.getDatetimeStt(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            if(z > 0 && z < 60)
            {
                holder.tvTime.setText("vài giây trước");
            } else if(z>=60 && z<3600)
            {
                holder.tvTime.setText((z/60)+" phút");
            } else if(z>=3600 && z<86399)
            {
                holder.tvTime.setText(z/3600+" giờ");
            } else if(z>=84000)
            {
                holder.tvTime.setText(z/84000+" ngày");
            }
            holder.tvLocate.setText(s.getLocationStt());
            holder.tvContent.setText(s.getContentStt());
            holder.imgC.setImageBitmap(StringToBitMap(s.getBitImgStt()));

    }

    @Override
    public int getItemCount() {
        return listS.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgA, imgC, imgLike;
        private TextView tvUser,tvTime, tvLocate, tvContent, tvLike, tvName;
        private LinearLayout btnLike, btnComment, btnShare;
        private ImageButton btnSendCmt, btnBack;
        private EditText edtCmt;
        private RecyclerView listCmt;
        private int position;

        int a = 0;
        Boolean b = true, c = false;
        CmtAdapter cmtAdapter;

        @SuppressLint("SimpleDateFormat")
        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            imgA = itemView.findViewById(R.id.avatarStt);
            imgC = itemView.findViewById(R.id.imageSttItem);
            tvUser = itemView.findViewById(R.id.tvUserMaster);
            tvName = itemView.findViewById(R.id.tvUserName);
            tvTime = itemView.findViewById(R.id.timeStt);
            tvLocate = itemView.findViewById(R.id.tvLocationSttItem);
            tvContent = itemView.findViewById(R.id.contenSttItems);
            btnLike = itemView.findViewById(R.id.bLike);
            tvLike = itemView.findViewById(R.id.txtLike);
            imgLike = itemView.findViewById(R.id.heartLike);
            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (b) {
                        b = false;
                        tvLike.setText("");
                        imgLike.setBackgroundResource(R.drawable.heartok);
                        AttackLike(position);
                    } else {
                        b = true;
                        tvLike.setText("Yêu thích");
                        imgLike.setBackgroundResource(R.drawable.heart);
                        AttackDiskLike(position);
                    }
                }

            });
            btnComment = itemView.findViewById(R.id.bComment);
            btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogComment(position);
                }
            });
            btnShare = itemView.findViewById(R.id.bShare);
        }
        public void AttackLike(int pos) {
            DatabaseReference databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Status");
            Query mDataRef = FirebaseDatabase.getInstance().getReference("Status").orderByChild("usermaster").equalTo(tvUser.getText().toString());
            mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        if (s.child("datetimeStt").getValue().toString().equals(listS.get(pos).datetimeStt)) {
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

        public void AttackDiskLike(int pos) {
            Query mQuery = FirebaseDatabase.getInstance().getReference("Status").orderByChild("usermaster").equalTo(tvUser.getText().toString());
            mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        if (s.child("datetimeStt").getValue().toString().equals(listS.get(pos).datetimeStt)) {
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

        public void DialogComment(int pos) {
            Dialog dia_cmt = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
            dia_cmt.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dia_cmt.setContentView(R.layout.dia_comment);
            dia_cmt.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            listCmt = (RecyclerView) dia_cmt.findViewById(R.id.listCmtStt);
            //
            cmtAdapter = new CmtAdapter(context, getFBaseCmt(position));
            LinearLayoutManager verticalLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            listCmt.setLayoutManager(verticalLayoutManagaer);
            listCmt.setAdapter(cmtAdapter);
            cmtAdapter.notifyDataSetChanged();
            //
            edtCmt = (EditText) dia_cmt.findViewById(R.id.edtCmt);
            edtCmt.findFocus();
            //
            btnSendCmt = (ImageButton) dia_cmt.findViewById(R.id.sendCmt);
            btnSendCmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        upCmt(pos);
                        hideKeyboardFrom(context, edtCmt);

                }
            });
            //
            btnBack = dia_cmt.findViewById(R.id.btnbackCmt);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dia_cmt.dismiss();
                }
            });
            dia_cmt.show();
        }

        public void upCmt(int pos) {
                String datetimeCmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                DatabaseReference databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Status");
                Query mDataRef = FirebaseDatabase.getInstance().getReference("Status").orderByChild("usermaster").equalTo(tvUser.getText().toString());
                mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot s : snapshot.getChildren()) {
                            if (s.child("datetimeStt").getValue().toString().equals(listS.get(pos).datetimeStt)) {
                                Comments comments = new Comments(edtCmt.getText().toString(), MainActivity.nameAcc, datetimeCmt, "abc");
                                databaseReference.child(s.getKey()).child("Comments").push().setValue(comments, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        edtCmt.setText("");
                                        cmtAdapter.notifyDataSetChanged();
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

        public ArrayList<Comments> getFBaseCmt(int pos) {
            ArrayList<Comments> coms = new ArrayList<>();
            coms.clear();
            DatabaseReference databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Status");
            Query mDataRef = FirebaseDatabase.getInstance().getReference("Status").orderByChild("usermaster").equalTo(tvUser.getText().toString());
            mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        if (s.child("datetimeStt").getValue().toString().equals(listS.get(pos).datetimeStt)) {
                            databaseReference.child(s.getKey()).child("Comments").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    if(snapshot2.exists()) {
                                        for (DataSnapshot d : snapshot2.getChildren()) {
                                            Comments com = d.getValue(Comments.class);
                                            coms.add(com);
                                        }
                                    }
                                    cmtAdapter.notifyDataSetChanged();
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
            return coms;
        }
        public void getAvatar(String s) {
            DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Profile").child(s);
            mdata.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        USER user = snapshot.getValue(USER.class);
                        tvName.setText(user.getsName());
                        imgA.setImageBitmap(StringToBitMap(user.getsImageA()));
                        notifyDataSetChanged();
                    }
                    else {
                        tvName.setText(MainActivity.nameAcc);
                        imgA.setBackgroundResource(R.mipmap.linkavatar);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return result;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
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
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
