package com.zhiyi.chinaipo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

//import com.zhiyi.chinaipo.mvp.main.fragment.data.DataFragment;
//import com.zhiyi.chinaipo.mvp.main.fragment.market.MarketFragment;
//import com.zhiyi.chinaipo.mvp.main.fragment.news.HomeFragment;
//import com.zhiyi.chinaipo.mvp.main.fragment.profile.ProfileFragment;

import com.zhiyi.chinaipo.ui.fragment.BrandFragment;
import com.zhiyi.chinaipo.ui.fragment.DataFragment;
import com.zhiyi.chinaipo.ui.fragment.HomeFragment;
import com.zhiyi.chinaipo.ui.fragment.MarketFragment;
import com.zhiyi.chinaipo.ui.fragment.ProfileFragment;

import java.util.ArrayList;

/**
 *
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;

    public MainViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        fragments.clear();
        fragments.add(new HomeFragment());
        fragments.add(new DataFragment());
        fragments.add(new MarketFragment());
        fragments.add(new BrandFragment());
        fragments.add(new ProfileFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    /**
     * Get the current fragment
     */
    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}