package com.uniksame.uniksamelearningpoint.ui.main;

import android.content.Context;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.uniksame.uniksamelearningpoint.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return mFragmentList.get(position);
    }

    public void addFrag(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return mFragmentList.size();
    }
}