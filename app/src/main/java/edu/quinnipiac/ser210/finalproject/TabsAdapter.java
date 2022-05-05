/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */

/*
TabsAdapter class. Handles swiping between the refrigeratorFragment and pantryFragment.
Author: Jonathan Mason
 */
package edu.quinnipiac.ser210.finalproject;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int numTabs;
    Context context;
    private ArrayList<Ingredient> mRefrigeratorData;
    private ArrayList<Ingredient> mPantryData;
    private IngredientListFragment refrigeratorFragment, pantryFragment;
    public TabsAdapter(FragmentManager fm, int numTabs, Context context) {
        super(fm);
        this.numTabs = numTabs;
        this.context = context;
        refrigeratorFragment = new IngredientListFragment();
        pantryFragment = new IngredientListFragment();
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:{
                if(mRefrigeratorData != null){
                    refrigeratorFragment.setIngredientData(mRefrigeratorData);
                }
                return refrigeratorFragment;
            }
            case 1:{
                if(mPantryData != null){
                    pantryFragment.setIngredientData(mPantryData);
                }
                return pantryFragment;
            }
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

    public void setRefrigeratorData(ArrayList<Ingredient> refrigeratorData){
        if(refrigeratorData != null){
            mRefrigeratorData = refrigeratorData;
            refrigeratorFragment.replaceIngredientData(mRefrigeratorData);
        }
    }

    public void setPantryData(ArrayList<Ingredient> pantryData){
        if(pantryData != null){
            mPantryData = pantryData;
            pantryFragment.replaceIngredientData(mPantryData);
        }
    }
}
