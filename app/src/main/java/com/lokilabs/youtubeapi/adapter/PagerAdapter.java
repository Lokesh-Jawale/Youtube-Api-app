package com.lokilabs.youtubeapi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lokilabs.youtubeapi.fragments.ChannelFragment;
import com.lokilabs.youtubeapi.fragments.PlayListFragment;
import com.lokilabs.youtubeapi.fragments.VideosFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int tabNumber;

    public PagerAdapter(FragmentManager fm,int tabNumber) {
        super(fm);
        this.tabNumber = tabNumber;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                VideosFragment videosFragment = new VideosFragment();
                return videosFragment;
            case 1:
                ChannelFragment channelFragment = new ChannelFragment();
                return channelFragment;
            case 2:
                PlayListFragment playListFragment = new PlayListFragment();
                return playListFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabNumber;
    }
}
