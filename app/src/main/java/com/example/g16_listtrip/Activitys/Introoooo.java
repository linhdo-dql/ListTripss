package com.example.g16_listtrip.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.g16_listtrip.R;

public class Introoooo extends AppCompatActivity implements Animation.AnimationListener{
    CountDownTimer count;
    private static final int REQUEST_ID_READ_WRITE_PERMISSION = 99;
    ImageView imgchu, imaganh;
    Animation animleft;
    Animation animright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introoooo);
        getView();
        anhanim();
        hinhanim();
        CameraAPIpemission();
        chuyenactivity();
        }
    public void chuyenactivity ()
    {
        count = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent4 = new Intent(Introoooo.this, SignIn.class);
                startActivity(intent4);
            }
        }.start();
    }
    public void getView() {
        imgchu = (ImageView) findViewById(R.id.chuo);
        imaganh = (ImageView) findViewById(R.id.anho);
    }
    public void anhanim() {
        animleft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideleft);
        imgchu.startAnimation(animleft);
    }
    public void hinhanim() {
        animright = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideright);
        imaganh.startAnimation(animright);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void CameraAPIpemission() {
        if(Build.VERSION.SDK_INT >= 23)
        {
            int read = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int write = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(read != PackageManager.PERMISSION_GRANTED || write != PackageManager.PERMISSION_GRANTED)
            {
                this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_ID_READ_WRITE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_ID_READ_WRITE_PERMISSION:
                if(grantResults.length >1 && grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED )
                {
                    Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}