package edu.quinnipiac.ser210.finalproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.app.FragmentManager;
import android.app.Instrumentation;
import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    private MainActivity mMainActivity;
    private Instrumentation instr;

    public MainActivityTest(){
        instr = getInstrumentation();
    }

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception{ ;
        mMainActivity = mainActivityActivityTestRule.getActivity();

    }

    @Test
    public void onNavigationItemSelected() {
        FragmentManager fragmentManager = mMainActivity.getFragmentManager();
        Log.d("Count", String.valueOf(fragmentManager.getFragments().size()));
        onView(withId(R.id.drawer)).perform(DrawerActions.open());
        onView(withId(R.id.drawer)).check(matches(isOpen()));
        onView(withId(R.id.navigationView)).perform(NavigationViewActions.navigateTo(R.id.nav_about));
        Log.d("Count", String.valueOf(fragmentManager.getFragments().size()));
        Log.d("Count", String.valueOf(fragmentManager.getFragments().size()));
        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.drawer)).perform(DrawerActions.open());
        onView(withId(R.id.navigationView)).perform(NavigationViewActions.navigateTo(R.id.nav_search_ingredients));
        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.drawer)).perform(DrawerActions.open());
        onView(withId(R.id.navigationView)).perform(NavigationViewActions.navigateTo(R.id.nav_search_recipes));
        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.drawer)).perform(DrawerActions.open());
        onView(withId(R.id.navigationView)).perform(NavigationViewActions.navigateTo(R.id.nav_favorite_recipes));
        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.drawer)).perform(DrawerActions.open());
        onView(withId(R.id.navigationView)).perform(NavigationViewActions.navigateTo(R.id.nav_food_items));
    }
}