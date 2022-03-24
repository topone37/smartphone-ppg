package com.uni.ppg.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.uni.ppg.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    private ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void canDisplayFingerprint() {
        onView(withId(R.id.btn_start_measurement)).check(matches(isDisplayed()));
    }

    @Test
    public void canDisplayHeartRate() {
        onView(withId(R.id.text_heart_rate)).check(matches(isDisplayed()));
    }

    @Test
    public void canDisplayHeartSymbol() {
        onView(withId(R.id.img_heart)).check(matches(isDisplayed()));
    }
}