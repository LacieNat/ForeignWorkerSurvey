package com.elasticbeanstalk.laciecool.first;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lacie on 1/18/16.
 */
public class IntroPageAdaptor extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public IntroPageAdaptor(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
