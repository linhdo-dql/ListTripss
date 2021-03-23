package com.example.g16_listtrip.Activitys;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.g16_listtrip.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabHost tabHost;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        //
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackground(new ColorDrawable(Color.TRANSPARENT));
        setSupportActionBar(toolbar);
        //
        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec tabHome = tabHost.newTabSpec("Home");
        tabHome.setIndicator("", getResources().getDrawable(R.drawable.round_home_20));
        tabHome.setContent(R.id.tab1);
        TabHost.TabSpec tabList = tabHost.newTabSpec("List");
        tabList.setIndicator("", getResources().getDrawable(R.drawable.round_menu_book_20));
        tabList.setContent(R.id.tab2);
        TabHost.TabSpec tabGPS = tabHost.newTabSpec("GPS");
        tabGPS.setIndicator("", getResources().getDrawable(R.drawable.baseline_room_24));
        tabGPS.setContent(R.id.tab3);
        TabHost.TabSpec tabNotify = tabHost.newTabSpec("Notify");
        tabNotify.setIndicator("", getResources().getDrawable(R.drawable.round_notifications_20));
        tabNotify.setContent(R.id.tab4);
        TabHost.TabSpec tabMenu = tabHost.newTabSpec("Menu");
        tabMenu.setIndicator("", getResources().getDrawable(R.drawable.round_menu_20));
        tabMenu.setContent(R.id.tab5);
        tabHost.addTab(tabHome);
        tabHost.addTab(tabList);
        tabHost.addTab(tabGPS);
        tabHost.addTab(tabNotify);
        tabHost.addTab(tabMenu);
    }

}