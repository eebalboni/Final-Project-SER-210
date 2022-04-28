package edu.quinnipiac.ser210.finalproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void onNavigationItemSelected() {
        onView(withId(R.id.drawer)).perform(DrawerActions.open());
        onView(withId(R.id.drawer)).check(matches(isOpen()));
        onView(withId(R.id.navigationView)).perform(NavigationViewActions.navigateTo(R.id.nav_about));
    }
}