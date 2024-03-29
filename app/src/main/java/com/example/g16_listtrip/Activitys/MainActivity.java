 package com.example.g16_listtrip.Activitys;

 import android.annotation.SuppressLint;
 import android.app.Dialog;
 import android.content.Intent;
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.graphics.Color;
 import android.graphics.drawable.ColorDrawable;
 import android.net.Uri;
 import android.os.Build;
 import android.os.Bundle;
 import android.provider.MediaStore;
 import android.util.Base64;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.ImageView;
 import android.widget.PopupMenu;
 import android.widget.RelativeLayout;
 import android.widget.TextView;

 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.annotation.RequiresApi;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.appcompat.widget.Toolbar;
 import androidx.viewpager.widget.ViewPager;

 import com.example.g16_listtrip.Adapter.PrimaryGraphicAdapter;
 import com.example.g16_listtrip.Adapter.StatusAdapter;
 import com.example.g16_listtrip.DoiTuong.Status;
 import com.example.g16_listtrip.DoiTuong.USER;
 import com.example.g16_listtrip.R;
 import com.google.android.material.tabs.TabLayout;
 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.ValueEventListener;

 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;

 import gun0912.tedbottompicker.TedBottomPicker;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 123;
    TabLayout tabLayout;
    Toolbar toolbar;
    ImageButton btnphoto,btntakept,btnaddlocation;
    ImageView avatar, imgSttReview, avatarAccStt;
    TextView tvAccStt;
    String imgStt = "";
    Button btnaddStt;
    EditText edtstt, edtlocation;
    RelativeLayout upStt;
    StatusAdapter adapter;
    DatabaseReference databaseReference;
    String stt = "", loc = "", datetimeStt ="";
    public static String nameAcc = "";
    private int tabIcon1[] = {R.drawable.baseline_home_20,
                             R.drawable.round_assignment_24,
                             R.drawable.baseline_room_24,
                             R.drawable.round_notifications_20,
                             R.drawable.round_menu_20};
    private int tabIcon[] = {R.drawable.outline_home_24,
            R.drawable.outline_assignment_24,
            R.drawable.outline_room_24,
            R.drawable.round_notifications_none_24,
            R.drawable.round_menu_20};
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
        getView();
        getAccountName();
        getAvatar();
        setIconA();
    }


    @SuppressLint("WrongViewCast")
    public void getView() {
        avatar = (ImageView) findViewById(R.id.avatar);
        viewPager = (ViewPager) findViewById(R.id.viewpaper);
        viewPager.setAdapter(new PrimaryGraphicAdapter(getSupportFragmentManager()));
        viewPager.setBackground(new ColorDrawable(Color.TRANSPARENT));
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        upStt = (RelativeLayout) findViewById(R.id.btnupstt);
    }




    public void getAccountName() {
        Intent a = getIntent();
        Bundle b = a.getExtras();
        nameAcc = b.getString("tk2");
    }
    public void setIconA() {
        tabLayout.getTabAt(0).setIcon(tabIcon1[0]);
        for(int i = 1; i < 5 ; i ++)
        {
            tabLayout.getTabAt(i).setIcon(tabIcon[i]);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(tabIcon1[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(tabIcon[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void PopupMenuAvatar(View view) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, avatar);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.mnLogup:
                        startActivity(new Intent(MainActivity.this, SignIn.class));
                        break;
                    case R.id.mnhelp:
                        startActivity(new Intent(MainActivity.this, PersonPage.class));
                }
                return true;
            }
        });
        popupMenu.show();
    }
    @SuppressLint("ResourceType")
    public void upStt(View view){

        Dialog diaupsst =  new Dialog(MainActivity.this,R.style.ThemeOverlay_AppCompat_Dialog);
        diaupsst.setContentView(R.layout.dialogupstt);
        btntakept = (ImageButton) diaupsst.findViewById(R.id.btnTakeImage);
        btnphoto = (ImageButton) diaupsst.findViewById(R.id.btnAddImgStt);
        btnaddlocation = (ImageButton) diaupsst.findViewById(R.id.btnaddLocation);
        edtstt = (EditText) diaupsst.findViewById(R.id.edtStt);
        tvAccStt = (TextView) diaupsst.findViewById(R.id.tvTkStt);
        imgSttReview = (ImageView) diaupsst.findViewById(R.id.imagSttReview);

        edtlocation = (EditText) diaupsst.findViewById(R.id.edtlocation);
        btnaddStt = (Button) diaupsst.findViewById(R.id.btnAddStt);
        btntakept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),REQUEST_IMAGE_CAPTURE);
            }
        });
        btnphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgStt = convertBitmapToString(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                };
                TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(MainActivity.this).setOnImageSelectedListener(listener).create();
                tedBottomPicker.show(getSupportFragmentManager());

            }
        });
        diaupsst.show();
        btnaddlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnaddlocation.setVisibility(View.INVISIBLE);
                edtlocation.setVisibility(View.VISIBLE);
            }
        });
        btnaddStt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                datetimeStt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                stt = edtstt.getText().toString();
                loc = edtlocation.getText().toString();
                databaseReference = FirebaseDatabase.getInstance().getReference("Status");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Status status = new Status();
                        status.usermaster = nameAcc;
                        status.contentStt = stt;
                        status.bitImgStt = imgStt;
                        status.locationStt = loc;
                        status.datetimeStt = datetimeStt;
                        databaseReference.push().setValue(status, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                diaupsst.dismiss();
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

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

    public void getAvatar() {
        DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Profile").child(MainActivity.nameAcc);
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    USER user = snapshot.getValue(USER.class);
                    avatar.setImageBitmap(StringToBitMap(user.getsImageA()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgStt = convertBitmapToString(imageBitmap);
        }
    }

}