package edu.temple.webbrowser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shiloh on 11/7/2017.
 */

class myAdapter extends FragmentStatePagerAdapter{

    ArrayList<BrowserFragment> fragments;
    ArrayList<String> urlStrings;
    int listIndex =0;
    int NUM_FRAGS =1;


    public myAdapter(FragmentManager fm, ArrayList<BrowserFragment> fragments, ArrayList<String> urlStrings) {
        super(fm);
        this.fragments = fragments;
        this.urlStrings = urlStrings;


    }






    @Override
    public Fragment getItem(int position) {
        switch(position){

            default:
                BrowserFragment newFragment = new BrowserFragment();
                fragments.add(listIndex, newFragment);
                listIndex++;
                return newFragment;
        }
    }

    @Override
    public int getCount() {
        return NUM_FRAGS;
    }




}
