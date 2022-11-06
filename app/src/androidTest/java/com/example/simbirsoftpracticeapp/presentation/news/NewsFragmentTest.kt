package com.example.simbirsoftpracticeapp.presentation.news

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.presentation.main.MainActivity
import com.example.simbirsoftpracticeapp.presentation.news.adapters.NewsAdapter
import com.example.simbirsoftpracticeapp.presentation.utils.EspressoUtils.atPosition
import com.example.simbirsoftpracticeapp.presentation.utils.EspressoUtils.waitFor
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsFragmentTest {

    @get:Rule
    val activityActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        launchFragmentInContainer<NewsFragment>(bundleOf(), R.style.Theme_SimbirsoftPracticeApp)
    }

    @Test
    fun progressBarVisibilityTest() {
        onView(withId(R.id.progress_bar))
            .check(matches(isDisplayed()))
        onView(isRoot()).perform(waitFor(3000))
        onView(withId(R.id.progress_bar))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun recyclerViewTest() {
        onView(isRoot()).perform(waitFor(2000))
        onView(withId(R.id.rv_events))
            .check(matches(isDisplayed()))
            .check(matches(atPosition(0, hasDescendant(not(withText(""))))))
            .check(matches(atPosition(0, isClickable())))
            .perform(RecyclerViewActions.scrollToPosition<NewsAdapter.NewsHolder>(3))
            .check(matches(atPosition(3, hasDescendant(not(withText(""))))))
    }
}