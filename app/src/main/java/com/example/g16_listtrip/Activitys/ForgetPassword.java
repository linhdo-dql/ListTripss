package com.example.g16_listtrip.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g16_listtrip.DoiTuong.ForgetPass;
import com.example.g16_listtrip.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ForgetPassword extends AppCompatActivity {
    int ranCap = 0;
    Button sendqmk;
    EditText edtqmktk, edtqmkemail, edtcapcha;
    TextView txtCap;
    DatabaseReference mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getView();
        RanCapCha();
    }
    public void getView() {
        txtCap = (TextView) findViewById(R.id.ranCap);
        edtqmkemail = (EditText) findViewById(R.id.edtemailqmk);
        edtqmktk = (EditText) findViewById(R.id.edttkqmk);
        edtcapcha = (EditText) findViewById(R.id.edtcapchaqmk);
        sendqmk = (Button) findViewById(R.id.btnqmk);
    }
    public void RanCapCha() {
        Random ran = new Random();
        ranCap = ran.nextInt((9999-1000)+1)+1000;
        txtCap.setText(""+ranCap);
    }
    public void fgPass(View view)
    {
        if(edtcapcha.getText().toString().trim().equals(txtCap.getText()))
        {
            fpRefer();
        }
    }
    public void fpRefer() {
        mData = FirebaseDatabase.getInstance().getReference().child("USER").child(edtqmktk.getText().toString().trim());
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null)
                {
                    mData = FirebaseDatabase.getInstance().getReference().child("ForgetPassword").child(edtqmktk.getText().toString().trim());
                    mData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue()==null) {
                                ForgetPass fg = new ForgetPass();
                                fg.email = edtqmkemail.getText().toString().trim();
                                fg.taikhoan = edtqmktk.getText().toString().trim();
                                mData.setValue(fg, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(ForgetPassword.this, "Yêu cầu của bạn đã được gửi!", Toast.LENGTH_SHORT).show();
                                        reForm();
                                    }
                                });
                            }
                            else {
                                reForm();
                                mData.removeValue();
                                Toast.makeText(ForgetPassword.this, "Bạn đã yêu cầu gần đây, vui lòng chờ trong giây lát để gửi lại yêu cầu!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    Toast.makeText(ForgetPassword.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void reForm()
    {
        edtqmkemail.setText("");
        edtqmktk.setText("");
        edtcapcha.setText("");
        RanCapCha();
    }

}