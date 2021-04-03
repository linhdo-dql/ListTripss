package com.example.g16_listtrip.Activitys;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g16_listtrip.Adapter.StatusAdapter;
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
import java.io.IOException;
import java.util.ArrayList;

import gun0912.tedbottompicker.TedBottomPicker;

public class PersonPage extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 123;
    ScrollView scrollView;
    RecyclerView listSttprf;
    StatusAdapter adapterStt2;
    RelativeLayout rlayoutAds, rlayoutFull, btnCreNewProf, btnEditPrf;
    String imgA = "", imgC = "";
    public ImageView imgcover;
    public ImageView avatarProfile;
    Button btnAddprofile;
    EditText edtName, edtBirth, edtJob, edtAdress, edtStudy, edtRelationship;
    TextView tvNamePrf, tvAdressprf, tvJobprf, tvRelaprf, tvSchoolprf, tvBirthprf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_page);
        getView();
        LinearLayoutManager verticalLayoutManagaer = new LinearLayoutManager(PersonPage.this, LinearLayoutManager.VERTICAL, false);
        listSttprf.setLayoutManager(verticalLayoutManagaer);
        adapterStt2 = new StatusAdapter(PersonPage.this, getFBASEsttUSER());
        listSttprf.setAdapter(adapterStt2);
        adapterStt2.notifyDataSetChanged();
        getProfile();

    }
    public void getView(){
        scrollView = (ScrollView) this.findViewById(R.id.scrollView3);
        listSttprf = (RecyclerView) findViewById(R.id.listSttUser);
        rlayoutAds = (RelativeLayout) findViewById(R.id.layoutADs);
        rlayoutFull = (RelativeLayout) findViewById(R.id.layoutfull);
        btnCreNewProf = (RelativeLayout) findViewById(R.id.crNprf);
        avatarProfile = (ImageView) findViewById(R.id.avatarProfile);
        imgcover = (ImageView) findViewById(R.id.imgCover);
        tvNamePrf = (TextView) findViewById(R.id.fullName);
        tvJobprf = (TextView) findViewById(R.id.tvWokdprf);
        tvSchoolprf = (TextView) findViewById(R.id.tvStudyprf);
        tvBirthprf = (TextView) findViewById(R.id.tvDateBprf);
        tvRelaprf = (TextView) findViewById(R.id.tvRelationprf);
        tvAdressprf = (TextView) findViewById(R.id.tvAdressprf);
        btnEditPrf = (RelativeLayout) findViewById(R.id.btnEditprf);
    }
    public ArrayList<Status> getFBASEsttUSER() {
        ArrayList<Status> s = new ArrayList<>();
        Query mQue = FirebaseDatabase.getInstance().getReference("Status").orderByChild("usermaster").equalTo(MainActivity.nameAcc);
        mQue.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren())
                {
                   Status c = snapshot1.getValue(Status.class);
                   s.add(c);
                }
                adapterStt2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return s;
    }
    public void CreateNewProfile(View view)
    {
        Dia_Add_Profile();

    }
    public void EditAvatar(View v)
    {
        PopupMenu popupMenu = new PopupMenu(PersonPage.this, avatarProfile);
        popupMenu.getMenuInflater().inflate(R.menu.menu_avatar_profile, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.mnviewAvartar:

                        break;
                    case R.id.mneditAvartar:
                        TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(Uri uri) {
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                    avatarProfile.setImageBitmap(bitmap);
                                    imgA = convertBitmapToString(bitmap);
                                    changeImage("sImageA",imgA);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(PersonPage.this).setOnImageSelectedListener(listener).create();
                        tedBottomPicker.show(getSupportFragmentManager());
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void EditCover(View v)
    {
        PopupMenu popupMenu1 = new PopupMenu(PersonPage.this, imgcover);
        popupMenu1.setGravity(Gravity.END);
        popupMenu1.getMenuInflater().inflate(R.menu.menu_cover_profile, popupMenu1.getMenu());
        popupMenu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.mnviewCover:

                        break;
                    case R.id.mneditCover:
                        TedBottomPicker.OnImageSelectedListener listener1 = new TedBottomPicker.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(Uri uri) {
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                    imgcover.setImageBitmap(bitmap);
                                    imgC = convertBitmapToString(bitmap);
                                    changeImage("sImageC",imgC);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        TedBottomPicker tedBottomPicker1 = new TedBottomPicker.Builder(PersonPage.this).setOnImageSelectedListener(listener1).create();
                        tedBottomPicker1.show(getSupportFragmentManager());
                        break;
                }
                return true;
            }
        });
        popupMenu1.show();


    }
    public void Dia_Add_Profile(){
        Log.e("c",imgC);
        Log.e("a",imgA);
        Dialog dia = new Dialog(PersonPage.this);
        dia.setContentView(R.layout.dia_newprf);
        dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        edtName = dia.findViewById(R.id.etNameP);
        edtBirth = dia.findViewById(R.id.etDateBP);
        edtAdress = dia.findViewById(R.id.etAdreesB);
        edtJob = dia.findViewById(R.id.etJobB);
        edtRelationship = dia.findViewById(R.id.etRelaP);
        edtStudy = dia.findViewById(R.id.etSchoolB);
        btnAddprofile = dia.findViewById(R.id.btnAddpro);
        dia.show();
        btnAddprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
    }
    public void editMyprf(View view) {
        Dia_Add_Profile();
    }
    public void editProfile() {
            if(imgA == "" || imgC == "")
            {
                imgA = convertBitmapToString(((BitmapDrawable)avatarProfile.getDrawable()).getBitmap());
                imgC = convertBitmapToString(((BitmapDrawable)imgcover.getDrawable()).getBitmap());
            }
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profile").child(MainActivity.nameAcc);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                        USER user = new USER();
                        user.sName = edtName.getText().toString();
                        user.sDateBirth =  edtBirth.getText().toString();
                        user.sAdrress = edtAdress.getText().toString();
                        user.sJob = edtJob.getText().toString();
                        user.sStudy = edtStudy.getText().toString();
                        user.sRelation = edtRelationship.getText().toString();
                        user.sImageA = imgA;
                        user.sImageC = imgC;
                        user.sAccount = MainActivity.nameAcc;
                        databaseReference.setValue(user, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(PersonPage.this, "Thành công", Toast.LENGTH_SHORT).show();
                            }
                        });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    public void getProfile()
    {
        DatabaseReference mDataref = FirebaseDatabase.getInstance().getReference("Profile").child(MainActivity.nameAcc);
        mDataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    rlayoutAds.setVisibility(View.VISIBLE);
                    rlayoutFull.setVisibility(View.VISIBLE);
                    USER u = snapshot.getValue(USER.class);
                    tvNamePrf.setText(u.getsName());
                    tvJobprf.setText(u.getsJob());
                    tvSchoolprf.setText(u.getsStudy());
                    tvBirthprf.setText(u.getsDateBirth());
                    tvRelaprf.setText(u.getsRelation());
                    tvAdressprf.setText(u.getsAdrress());
                    imgcover.setImageBitmap(StringToBitMap(u.getsImageC()));
                    avatarProfile.setImageBitmap(StringToBitMap(u.getsImageA()));
                }else {
                    btnCreNewProf.setVisibility(View.VISIBLE);
                    tvNamePrf.setText(MainActivity.nameAcc);
                    avatarProfile.setImageResource(R.mipmap.linkavatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void changeImage(String img, String value) {
        DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Profile").child(MainActivity.nameAcc);
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mdata.child(img).setValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

}