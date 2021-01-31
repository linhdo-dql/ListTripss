package com.example.g16_listtrip.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.g16_listtrip.R;

public class Introoooo extends AppCompatActivity implements Animation.AnimationListener{
    CountDownTimer count;
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
        chuyenactivity();
        }
    public void chuyenactivity ()
    {
        count = new CountDownTimer(3000, 1000) {
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
}