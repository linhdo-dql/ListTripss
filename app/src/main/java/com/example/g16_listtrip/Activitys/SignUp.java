package com.example.g16_listtrip.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g16_listtrip.DoiTuong.Accounts;
import com.example.g16_listtrip.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    DatabaseReference mData;
    EditText edtemaildk, edttkdk, edtmkdk, edtnhaplai;
    Button btnDk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        try {
            getView();
        }catch (Exception ex) {
            Toast.makeText(this, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void getView() {
        btnDk = (Button) findViewById(R.id.btnsignup);
        edtemaildk = (EditText) findViewById(R.id.edtemaildk);
        edttkdk = (EditText) findViewById(R.id.edttkdk);
        edtmkdk = (EditText) findViewById(R.id.edtmkdk);
    }

    public void signup(View v)
    {  try {
        mData = FirebaseDatabase.getInstance().getReference("USER").child(edttkdk.getText().toString().trim());
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    Accounts acc = new Accounts();
                    acc.username = edttkdk.getText().toString().trim();
                    acc.password = edtmkdk.getText().toString().trim();
                    acc.email = edtemaildk.getText().toString().trim();
                    mData.setValue(acc, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null) {
                                Toast.makeText(SignUp.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp.this, SignIn.class);
                                Bundle bun = new Bundle();
                                bun.putString("tentk", edttkdk.getText().toString().trim());
                                bun.putString("mkdk", edtmkdk.getText().toString().trim());
                                intent.putExtras(bun);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(SignUp.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }catch (Exception ex)
    {
        Toast.makeText(this, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    }

}