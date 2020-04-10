package com.example.newsapi.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.newsapi.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityKtTest {

    @Test
    fun testLaunchMainActivity() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.activity_main_container)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.main_container)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.toolbar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}