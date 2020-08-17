package com.example.itemgiveaway.model;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.itemgiveaway.fragment.AboutFragment;
import com.example.itemgiveaway.fragment.AccountFragment;
import com.example.itemgiveaway.fragment.ChatFragment;
import com.example.itemgiveaway.fragment.DonateFragment;
import com.example.itemgiveaway.fragment.NeedFragment;

public class PagerView extends FragmentPagerAdapter {
    public PagerView(FragmentManager fm) {
        super(fm, PagerView.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {

            case 0:
                fragment = new AboutFragment();
                break;
            case 1:
                fragment = new ChatFragment();
                break;
            case 2:
                fragment = new DonateFragment();
                break;
            case 3:
                fragment = new NeedFragment();
                break;
            default:
                fragment = new AccountFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}

