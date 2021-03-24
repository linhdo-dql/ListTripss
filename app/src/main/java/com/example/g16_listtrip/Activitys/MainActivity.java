package com.example.g16_listtrip.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.g16_listtrip.Adapter.PrimaryGraphicAdapter;
import com.example.g16_listtrip.Adapter.StoriesAdapter;
import com.example.g16_listtrip.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    Toolbar toolbar;
    TabHost tabHost;
    ImageButton avatar;
    ImageView addstory;
    TextView txtTtk;
    String nameAcc = "", bitImgS ="", timeS = "";
    private int tabIcon[] = {R.drawable.round_home_20,
                             R.drawable.round_menu_book_20,
                             R.drawable.baseline_room_24,
                             R.drawable.round_notifications_20,
                             R.drawable.round_menu_20};
    DatabaseReference mDataRef;
    RecyclerView recyclerView;
    StoriesAdapter storiesAdapter;
    ListView list;
    ViewPager viewPager;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //

        //
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackground(new ColorDrawable(Color.TRANSPARENT));
        setSupportActionBar(toolbar);
        //
        getView();
        setIconA();
        //getAccountName();
        //
       //count.start();


        /*storiesAdapter=new StoriesAdapter(this, getFBase());
        recyclerView.setAdapter(storiesAdapter);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        storiesAdapter.notifyDataSetChanged();*/

    }
    /*public void appGraphic() {
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
    }*/

    public void getView() {
      /*  addstory = (ImageView) findViewById(R.id.addstory);*/
        avatar = (ImageButton) findViewById(R.id.avatar);
        txtTtk = (TextView) findViewById(R.id.ttk);
        /*recyclerView = (RecyclerView) findViewById(R.id.liststr);
        list = (ListView) findViewById(R.id.liststt);*/
        viewPager = (ViewPager) findViewById(R.id.viewpaper);
        viewPager.setAdapter(new PrimaryGraphicAdapter(getSupportFragmentManager()));
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void getAccountName() {
        Intent a = getIntent();
        Bundle b = a.getExtras();
        nameAcc = b.getString("tk2");
        txtTtk.setText(""+nameAcc);
    }
    public void setIconA() {
        for(int i = 0; i < 5 ; i ++)
        {
            tabLayout.getTabAt(i).setIcon(tabIcon[i]);
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#FF6D00"),PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#76C8FF"),PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    /*@RequiresApi(api = Build.VERSION_CODES.O)
    public void sendStorytoFibase() {
        timeS = LocalTime.now().toString();
        mDataRef = FirebaseDatabase.getInstance().getReference("Story");
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Stories str = new Stories();
                    str.accS = nameAcc;
                    str.imgS = bitImgS;
                    str.timeS = timeS;
                    mDataRef.push().setValue(str, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        }
                    });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    */
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