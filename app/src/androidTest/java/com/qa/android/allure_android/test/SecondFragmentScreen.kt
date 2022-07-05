package com.qa.android.allure_android.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import com.qa.android.allure_android.MainActivity
import com.qa.android.allure_android.R
import com.qa.android.allure_android.util.BaseScreen
import io.qameta.allure.android.rules.LogcatRule
import io.qameta.allure.android.rules.ScreenshotRule
import io.qameta.allure.android.rules.WindowHierarchyRule
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import io.qameta.allure.kotlin.TmsLink
import io.qameta.allure.kotlin.junit4.DisplayName
import io.qameta.allure.kotlin.junit4.Tag
import org.junit.*
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AllureAndroidJUnit4::class)
@Epic("Allure Epic")
@Feature("Allure Feature")
@Story("Allure Second Activity Story")
@LargeTest
class SecondFragmentScreen : BaseScreen() {

    /**
     * Use [ActivityScenarioRule] to create and launch the activity under test before each test,
     * and close it after each test. This is a replacement for
     * [androidx.test.rule.ActivityTestRule].
     */
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @get:Rule
    val logcatRule = ScreenshotRule(mode = ScreenshotRule.Mode.FAILURE)
    @get:Rule
    val logcatRule2 = LogcatRule()

    @Before
    fun setUp() {
        // Initializes Intents
        Intents.init()
    }

    @After
    fun tearDown() {
        // Clears Intents state.
        Intents.release()
    }

    @Test
    @TmsLink("test-1")
    @Tag("Regression")
    @DisplayName("As a User, I should see the Second Screen")
    fun verifySecondScreen() {
        onView(withId(R.id.button_first))
            .check(matches(isClickable()))

        onView(withId(R.id.button_first))
            .perform(click())

        onView(withId(R.id.button_second))
            .check(matches(isDisplayed()))

    }

    @Test
    @TmsLink("test-1")
    @DisplayName("As a User, I should see the Second Screen with failed")
    fun verifySecondScreenWithFaliure() {
        onView(withId(R.id.button_first))
            .check(matches(isClickable()))

        onView(withId(R.id.button_first))
            .perform(click())

        onView(withId(R.id.button_first))
            .check(matches(isDisplayed()))

    }
}