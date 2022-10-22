package com.example.simbirsoftpracticeapp.presentation.news

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.common.Constants
import com.example.simbirsoftpracticeapp.presentation.main.MainActivity
import com.example.simbirsoftpracticeapp.presentation.utils.EspressoUtils
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NewsDetailFragmentTest {

    @get:Rule
    val activityActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        launchFragmentInContainer<NewsDetailFragment>(
            bundleOf(Constants.EVENT_ID to 1),
            R.style.Theme_SimbirsoftPracticeApp
        )
    }

    @Test
    fun testRootIsVisible() {
        onView(isRoot()).check(matches(isDisplayed()))
    }

    @Test
    fun progressBarVisibilityTest() {
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
        onView(isRoot()).perform(EspressoUtils.waitFor(3000))
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
    }

    @Test
    fun contentIsScrollingTest(){
        onView(isRoot())
            .check(matches())
    }
}