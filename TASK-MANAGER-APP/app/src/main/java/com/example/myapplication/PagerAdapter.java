package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    int behavior;
    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.behavior=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new DailyFragment();
            case 1 : return new WeeklyFragment();
            case 2 : return new MonthlyFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return behavior;
    }
}
