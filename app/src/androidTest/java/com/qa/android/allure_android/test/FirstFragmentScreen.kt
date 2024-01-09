package com.qa.android.allure_android.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.qa.android.allure_android.MainActivity
import com.qa.android.allure_android.R
import com.qa.android.allure_android.util.BaseScreen
import com.qa.android.allure_android.util.ScreenRecordingRuleChain
import com.qa.android.allure_android.util.ScreenShotRuleChain
import io.qameta.allure.android.rules.LogcatRule
import io.qameta.allure.android.rules.ScreenshotRule
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.*
import io.qameta.allure.kotlin.junit4.DisplayName
import io.qameta.allure.kotlin.junit4.Tag
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import com.qa.android.allure_android.dataset.testing.R as DatasetR

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AllureAndroidJUnit4::class)
@Epic("Allure Epic")
@Feature("Allure Feature")
@Story("Allure Main Activity Story")
@LargeTest
class FirstFragmentScreen : BaseScreen(){

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
    val screenRecordingRule: RuleChain = ScreenRecordingRuleChain()

    @get:Rule
    val screenShotRule: RuleChain = ScreenShotRuleChain()

    @Before
    fun setUp() {
        // Initializes Intents
        Intents.init()
        GenerateEnvironmentXml()
    }

    @After
    fun tearDown() {
        // Clears Intents state.
        Intents.release()
    }


    @Test
    @DisplayName("As a User, I should see the Home Screen")
    @Tag("Regression")
    fun verifyMainScreen() {
        val username = testContext.getString(DatasetR.string.td_username)
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.qa.android.allure_android", appContext.packageName)

        //Verify the text field is displayed
        onView(withId(R.id.textview_first))
            .check(matches(isDisplayed()))

    }
    @Test
    @DisplayName("As a User, I should be click on the Next button ")
    fun verifyNavigateSecondScreen() {
        onView(withId(R.id.button_first))
            .check(matches(isClickable()))

        onView(withId(R.id.button_first))
            .perform(click())
    }

    @Test
    @DisplayName("As a User, I should be click on the Next button with failure ")
    fun verifyNavigateSecondScreenWithFailure() {
        onView(withId(R.id.button_first))
            .check(matches(isClickable()))

        onView(withId(R.id.button_second))
            .perform(click())
    }

}