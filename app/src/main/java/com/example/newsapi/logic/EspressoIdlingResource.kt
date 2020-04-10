package com.example.newsapi.logic

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource { // TODO move to Debug build
    private const val RESOURCE = "GLOBAL"
    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}