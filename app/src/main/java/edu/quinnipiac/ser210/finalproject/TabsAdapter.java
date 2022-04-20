package edu.quinnipiac.ser210.finalproject;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int numTabs;
    Context context;
    public TabsAdapter(FragmentManager fm, int numTabs, Context context) {
        super(fm);
        this.numTabs = numTabs;
        this.context = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                //Set data for refrigerator
                return new IngredientListFragment();
            case 1:
                //Set data for pantry
                return new IngredientListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return context.getResources().getString(R.string.refrigerator_tab);
            case 1:
                return context.getResources().getString(R.string.pantry_tab);
            default:
                return null;
        }
    }
}
