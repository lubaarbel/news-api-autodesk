package com.example.newsapi.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.newsapi.R
import com.example.newsapi.logic.EspressoIdlingResource
import com.example.newsapi.paging.NewsItemHolder
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class NewsHomeFragmentKtTest {

    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testRecyclerViewVisibility() {
        onView(withId(R.id.swipeToRefresh)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.home_recycler_view)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testNewsSelectionFromList() {
        onView(withId(R.id.home_recycler_view))
                .perform(actionOnItemAtPosition<NewsItemHolder>(4, click()))
    }

    @Test
    fun testNewsSelectionFromListAndComeBackToTheList() {
        onView(withId(R.id.home_recycler_view))
                .perform(actionOnItemAtPosition<NewsItemHolder>(4, click()))
        pressBack()
        onView(withId(R.id.home_recycler_view)).check(matches(ViewMatchers.isDisplayed()))
    }
}
