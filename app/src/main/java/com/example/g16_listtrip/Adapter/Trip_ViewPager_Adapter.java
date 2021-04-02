package com.example.g16_listtrip.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.g16_listtrip.Activitys.Schedule1;
import com.example.g16_listtrip.Activitys.Schedule2;

public class Trip_ViewPager_Adapter extends FragmentStatePagerAdapter {
    Schedule1 schedule1;
    Schedule2 schedule2;
    private String[] trip = {"Chuyến đi hiện tại", "Chuyến đi của bạn"};
    public Trip_ViewPager_Adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        schedule1 = new Schedule1();
        schedule2 = new Schedule2();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
        {
            return schedule1;
        }else if(position==1){
            return schedule2;
        }
        return null;
    }

    @NonNull

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return trip[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
