package com.example.coverbackgroundsolution


import android.widget.Button
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {

    }

    @Test
    fun shouldExist_Executor_Button() {
        activityRule.scenario.onActivity {
            val strButton = it.findViewById<Button>(R.id.button_javathd)
            assertThat(strButton.text.toString(), equalTo("Executor"))
        }
    }
}