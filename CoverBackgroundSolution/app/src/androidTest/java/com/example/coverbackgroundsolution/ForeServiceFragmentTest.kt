package com.example.coverbackgroundsolution


import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.coverbackgroundsolution.ui.ForeServiceFragmentKo
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ForeServiceFragmentTest {
    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setUp() {
    }

    @Test
    fun shouldExist_ForeSVCStart_Button() {
        val fragmentArgs = Bundle().apply {
            putInt("selectedListItem", 0)
        }
        val factory = ForeServiceFragmentKo()
        val scenario = launchFragmentInContainer<ForeServiceFragmentKo>(
                fragmentArgs)
        onView(withId(R.id.button_start_svc)).check(matches(withText("ForeSVC Start")))

        // https://developer.android.com/training/testing/espresso/recipes
        onView(allOf(withText("ForeSVC Start"))).perform(click())

    }
}