package com.example.simbirsoftpracticeapp.presentation.profile

import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.simbirsoftpracticeapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest {

//    @get:Rule
//    val activityActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

//    @get:Rule
//    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        launchFragment()
    }

    @Test
    fun testInformationIsDisplayed() {
        onView(withId(R.id.info))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testUserIconsHasDrawable() {
        onView(withId(R.id.iv_friend_1))
            .check(matches(imageViewHasDrawable()))
        onView(withId(R.id.iv_friend_2))
            .check(matches(imageViewHasDrawable()))
        onView(withId(R.id.iv_friend_3))
            .check(matches(imageViewHasDrawable()))
    }

    @Test
    fun testAlertDialogIsOpening() {
        onView(withId(R.id.iv_profile_photo))
            .perform(click())
        onView(withText(R.string.pick_photo))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testProfileInformationFieldsNotEmpty() {
        onView(withId(R.id.tv_name)).check(
            matches(
                allOf(
                    isDisplayed(),
                    not(withText("")),
                    isNotClickable()
                )
            )
        )
        onView(withId(R.id.tv_sphere)).check(
            matches(
                allOf(
                    isDisplayed(),
                    not(withText("")),
                    isNotClickable()
                )
            )
        )
        onView(withId(R.id.tv_birthdate)).check(
            matches(
                allOf(
                    isDisplayed(),
                    not(withText("")),
                    isNotClickable()
                )
            )
        )
    }

    @Test
    fun checkDeletePhotoMenuOptionRemovePhotoFromProfile() {
        onView(withId(R.id.iv_profile_photo))
            .perform(click())
        onView(withText(R.string.delete))
            .perform(click())
        onView(withId(R.id.iv_profile_photo))
            .check(matches(not(isDisplayed())))
    }

    private fun launchFragment() =
        launchFragmentInContainer<ProfileFragment>(bundleOf(), R.style.Theme_SimbirsoftPracticeApp)

    private fun imageViewHasDrawable(): Matcher<View> {
        return object : Matcher<View> {
            override fun describeTo(description: Description?) {
            }

            override fun matches(item: Any?): Boolean {
                val view = item as View

                val imageView = view as ImageView

                return imageView.drawable != null
            }

            override fun describeMismatch(item: Any?, mismatchDescription: Description?) {

            }

            override fun _dont_implement_Matcher___instead_extend_BaseMatcher_() {
                TODO("Not yet implemented")
            }

        }
    }
}