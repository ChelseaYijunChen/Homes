package com.example.yijunchen.homes;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.FragmentTransaction;

import com.example.yijunchen.homes.activities.MainActivity;
import com.example.yijunchen.homes.fragments.CategoryTabFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by zhangwenpurdue on 7/10/2017.
 */

public class MainActivityTest {
    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void init() {
        // Specify a valid string.
        mainActivityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }
    @Test
    public void fragment_can_be_instantiated() {
        mainActivityActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CategoryTabFragment categoryTabFragment = startVoiceFragment();
            }
        });
        // Then use Espresso to test the Fragment
        onView(withId(R.id.main_fragment_container)).check(matches(isDisplayed()));
    }
    @Test
    public void tabClicked() {
        onView(withId(R.id.main_fragment_container)).check(matches(isDisplayed()));
        onView(withId(R.id.action_homepage)).check(matches(isDisplayed())).perform(click());

        onView(withText("Rent")).check(matches(isDisplayed())).perform(click());
        onView(withText("Outright Purchase")).check(matches(isDisplayed())).perform(click());
        onView(withText("Mortgage")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.action_search)).check(matches(isDisplayed())).perform(click());
        //pressBack();



    }
    @Test
    public void testRecycleView() {
        //通过文本RecycleView找到按钮，并执行点击事件，跳转到RecycleviewActivity
       // Espresso.onView(withId(R.id.rent_all_property_recycleview)).perform(RecyclerViewActions.scrollToPosition(7));
        onData(withId(R.id.categort_linear_layout)).atPosition(2).perform(click()).check(matches(isDisplayed()));
        onData(withId(R.id.categort_linear_layout)).inAdapterView(allOf(withId(R.id.property_list_img))).atPosition(2).onChildView(withId(R.id.image)).perform(click());

    }

    private CategoryTabFragment startVoiceFragment() {
        FragmentTransaction transaction = mainActivityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
        CategoryTabFragment voiceFragment = new CategoryTabFragment();
        transaction.add(voiceFragment, "voiceFragment");
        transaction.commit();
        return voiceFragment;
    }

}
