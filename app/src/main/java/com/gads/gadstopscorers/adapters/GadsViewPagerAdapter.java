package com.gads.gadstopscorers.adapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gads.gadstopscorers.ui.fragments.LeaderFragment;
import com.gads.gadstopscorers.ui.fragments.SkillsFragment;

public class GadsViewPagerAdapter extends FragmentPagerAdapter {

    public GadsViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @SuppressLint("Assert")
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                return new LeaderFragment();
            case 1:
                return new SkillsFragment();
        }
        assert false;
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Learning Leaders";
        }else {
            return "Skill IQ Leaders";
        }
    }

}
