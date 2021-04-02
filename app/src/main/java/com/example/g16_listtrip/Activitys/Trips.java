package com.example.g16_listtrip.Activitys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.g16_listtrip.Adapter.Trip_ViewPager_Adapter;
import com.example.g16_listtrip.DoiTuong.Trip;
import com.example.g16_listtrip.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Trips extends Fragment {
    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;
    private EditText edtlocationTrip, edtTimeTrip, edtCost, edtNumberPeople, edtTimeIntend;
    private Button btnAddTrip;
    private int mYear, mMonth, mDay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.trips, container,false);
        initView();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallDialog();
            }
        });
        return rootView;
    }
    public void initView(){
        tabLayout = rootView.findViewById(R.id.tabTrip);
        viewPager = rootView.findViewById(R.id.viewPagerTrip);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new Trip_ViewPager_Adapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);

    }
    public void CallDialog(){
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        dialog.setContentView(R.layout.dialogtrip);
        edtlocationTrip = dialog.findViewById(R.id.edtlocationTrip);
        edtCost = dialog.findViewById(R.id.edtCost);
        edtNumberPeople = dialog.findViewById(R.id.edtNumberPeople);
        edtTimeIntend = dialog.findViewById(R.id.edtTimeIntend);
        edtTimeTrip = dialog.findViewById(R.id.edtTimeTrip);
        ImageView imgPicker = dialog.findViewById(R.id.imgPicker);
        imgPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edtTimeIntend.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnAddTrip = dialog.findViewById(R.id.btnAddTrip);
        dialog.show();
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Trip").child(MainActivity.nameAcc);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Trip trip = new Trip(edtTimeTrip.getText().toString(), edtlocationTrip.getText().toString(), Integer.parseInt(edtCost.getText().toString()), Integer.parseInt(edtNumberPeople.getText().toString()),
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()), edtTimeIntend.getText().toString());
                        databaseReference.push().setValue(trip, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getActivity(), "Thanh cong", Toast.LENGTH_SHORT).show();
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
}
