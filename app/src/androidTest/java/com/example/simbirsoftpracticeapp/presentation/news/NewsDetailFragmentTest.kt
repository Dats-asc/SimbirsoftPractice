package com.example.simbirsoftpracticeapp.presentation.news

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.common.Constants
import com.example.simbirsoftpracticeapp.presentation.main.MainActivity
import com.example.simbirsoftpracticeapp.presentation.utils.EspressoUtils
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NewsDetailFragmentTest {

    @get:Rule
    val activityActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        launchFragmentInContainer<NewsDetailFragment>(
            bundleOf(Constants.EVENT_ID to 0),
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
        onView(isRoot()).perform(EspressoUtils.waitFor(1000))
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
    }

    @Test
    fun contentNotEmptyTest() {
        onView(isRoot()).perform(EspressoUtils.waitFor(100))
        onView(withId(R.id.tv_event_title)).check(matches(not(withText(""))))
        onView(withId(R.id.tv_end_date)).check(matches(not(withText(""))))
        onView(withId(R.id.tv_address)).check(matches(not(withText(""))))
        onView(withId(R.id.tv_phone_number)).check(matches(not(withText(""))))
    }

    @Test
    fun donationDialogIsDisplayedTest(){
        onView(withId(R.id.help_with_money))
            .check(matches(not(isDisplayed())))
        onView(isRoot()).perform(swipeUp())
        onView(withId(R.id.help_with_money))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.donation_dialog_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun donationDialogInputTest(){
        onView(isRoot()).perform(swipeUp())
        onView(withId(R.id.help_with_money))
            .perform(click())

        onView(withId(R.id.et_amount_of_money))
            .check(matches(isDisplayed()))
            .perform(typeText("char test"))
            .check(matches(withText("")))
            .perform(typeText("42"))
            .check(matches(withText("42")))
    }

    @Test
    fun donationDialogCheckCancelButton(){
        onView(isRoot()).perform(swipeUp())
        onView(withId(R.id.help_with_money))
            .perform(click())

        onView(withText(R.string.donation_dialog_title))
            .check(matches(isDisplayed()))
        onView(withText(R.string.cancel))
            .perform(click())
        onView(isRoot())
            .perform(click())
            .check(matches(isEnabled()))
    }

    @Test
    fun donationDialogCheckSendButton(){
        onView(isRoot()).perform(swipeUp())
        onView(withId(R.id.help_with_money))
            .perform(click())

        onView(withId(R.id.btn_send))
            .check(matches(isNotEnabled()))
        onView(withId(R.id.et_amount_of_money))
            .perform(typeText("string test"))
        onView(withId(R.id.btn_send))
            .check(matches(isNotEnabled()))
        onView(withId(R.id.et_amount_of_money))
            .perform(typeText("42"))
        onView(withId(R.id.btn_send))
            .check(matches(isEnabled()))
    }

    @Test
    fun donationDialogClosingTest(){
        onView(isRoot()).perform(swipeUp())
        onView(withId(R.id.help_with_money))
            .perform(click())
        onView(withId(R.id.et_amount_of_money))
            .perform(typeText("42"))
        onView(withId(R.id.btn_send))
            .perform(click())
        onView(isRoot()).check(matches(isEnabled()))
    }
}