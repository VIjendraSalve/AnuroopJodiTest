package com.wind.anuroopjodi.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.wind.anuroopjodi.InsideActivities.Fragmentsss.AdvancedSearchFragment;
import com.wind.anuroopjodi.InsideActivities.Fragmentsss.SimpleSearchFragment;



public class SearchTabAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public SearchTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                SimpleSearchFragment tab1 = new SimpleSearchFragment();
                return tab1;
            case 1:
                AdvancedSearchFragment tab2 = new AdvancedSearchFragment();
                return tab2;


            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }



    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}