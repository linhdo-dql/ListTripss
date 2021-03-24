package com.example.g16_listtrip.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.g16_listtrip.Activitys.GPS;
import com.example.g16_listtrip.Activitys.Home;
import com.example.g16_listtrip.Activitys.Notify;
import com.example.g16_listtrip.Activitys.Setting;
import com.example.g16_listtrip.Activitys.Trips;

public class PrimaryGraphicAdapter extends FragmentStatePagerAdapter {
    private String[] listTab = {"home","trips","gps","notify","setting"};
    private Home h;
    private Trips t;
    private GPS g;
    private Notify n;
    private Setting s;
    public PrimaryGraphicAdapter(@NonNull FragmentManager fm) {
        super(fm);
        h = new Home();
        t = new Trips();
        g = new GPS();
        n = new Notify();
        s = new Setting();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return h;
        }
        else if (position ==1)
        {
            return t;
        }
        else if(position == 2)
        {
            return g;
        }
        else if(position == 3)
        {
            return n;
        }
        else if(position == 4)
        {
            return s;
        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }


}
