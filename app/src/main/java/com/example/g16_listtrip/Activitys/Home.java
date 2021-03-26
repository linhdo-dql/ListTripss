package com.example.g16_listtrip.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g16_listtrip.Adapter.StatusAdapter;
import com.example.g16_listtrip.Adapter.StoriesAdapter;
import com.example.g16_listtrip.DoiTuong.Status;
import com.example.g16_listtrip.DoiTuong.Stories;
import com.example.g16_listtrip.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Home extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 123;
    private View rootView;
    ListView listStt;
    RecyclerView listStr;
    ImageButton imgUp;
    DatabaseReference mDataRef;
    StoriesAdapter storiesAdapter;
    StatusAdapter statusAdapter;
    String bitImgS = "", timeS = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home, container,false);
        initView();
        statusAdapter = new StatusAdapter(getActivity(), R.layout.itemstt, getFBaseSTT());
        listStt.setAdapter(statusAdapter);
        statusAdapter.notifyDataSetChanged();
        storiesAdapter=new StoriesAdapter(getActivity(), getFBaseSTR());
        listStr.setAdapter(storiesAdapter);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listStr.setLayoutManager(horizontalLayoutManagaer);
        imgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
            }
        });
        return rootView;
    }
    public void initView() {
        listStr = (RecyclerView) rootView.findViewById(R.id.listStr);
        listStt = (ListView) rootView.findViewById(R.id.listStt);
        imgUp = (ImageButton) rootView.findViewById(R.id.btnAddstr);
    }
    public ArrayList<Stories> getFBaseSTR() {
        ArrayList<Stories> Arrstr = new ArrayList<>();
        mDataRef = FirebaseDatabase.getInstance().getReference("Story");
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for( DataSnapshot snap : snapshot.getChildren())
                    {
                        Stories stories = snap.getValue(Stories.class);
                        Arrstr.add(stories);
                    }
                }
                storiesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return Arrstr;
    }
    public ArrayList<Status> getFBaseSTT() {
        ArrayList<Status> Arrstt = new ArrayList<>();
        mDataRef = FirebaseDatabase.getInstance().getReference("Status");
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for( DataSnapshot snap : snapshot.getChildren())
                    {
                        Status status = snap.getValue(Status.class);
                        Arrstt.add(status);
                    }
                }
                statusAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return Arrstt;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitImgS = convertBitmapToString(imageBitmap);
            sendStorytoFibase();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendStorytoFibase() {
        timeS = LocalDateTime.now().toString();
        mDataRef = FirebaseDatabase.getInstance().getReference("Story");
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Stories str = new Stories();
                str.accS = MainActivity.nameAcc;
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


    public void reloadH() {
        statusAdapter.notifyDataSetChanged();
        storiesAdapter.notifyDataSetChanged();
    }
}
