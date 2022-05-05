package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecipeDetailFragment extends Fragment {
    private Toolbar mToolbar;
    private ShareActionProvider mShareActionProvider;
    private ArrayList<Recipe> mRecipe;
    private Recipe recipe;
    private Bundle recipeBundle;
    private ArrayList<Recipe> mRecipeData;
    private RecipeDataSource rData;
    private String name,serving,ingredient,instruction;
    private Bundle b;
    private ShareActionProvider provider;

    public RecipeDetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         b = getArguments();
         name = getArguments().getString("name");
         serving = getArguments().getString("serving");
         ingredient = getArguments().getString("ingredient");
         instruction = getArguments().getString("instruction");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_recipe_detail,container,false);
        mToolbar = (Toolbar) layout.findViewById(R.id.toolbar);

        mToolbar.setNavigationIcon(R.drawable.ic_baseline_share_24);
        mToolbar.inflateMenu(R.menu.main_menu);

        TextView title = layout.findViewById(R.id.rName);
        title.setText(name);
        TextView servings = layout.findViewById(R.id.servingSize);
        servings.setText(serving);
        TextView ingredients = layout.findViewById(R.id.ingredients);
        ingredients.setText(ingredient);
        TextView direction = layout.findViewById(R.id.instruction);
        direction.setText(instruction);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        return layout;
    }

    public void onAttach(@NonNull Context context){

        super.onAttach(context);
    }

   public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        inflater.inflate(R.menu.main_menu,menu);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider((MenuItem)menu.findItem(R.id.action_share));
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.action_share:
                ApplicationInfo info = getActivity().getApplicationInfo();
                String apPath = info.sourceDir;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra("name",name);
                intent.putExtra("serving", serving);
                intent.putExtra("ingredient", ingredient);
                intent.putExtra("instruction",instruction);
                provider.setShareIntent(intent);
                return true;
        }
        return false;
    }


}