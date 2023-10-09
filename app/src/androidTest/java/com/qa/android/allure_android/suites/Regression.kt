package com.qa.android.allure_android.suites

import com.qa.android.allure_android.test.FirstFragmentScreen
import com.qa.android.allure_android.test.SecondFragmentScreen
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    FirstFragmentScreen::class,
    SecondFragmentScreen::class
)
class Regression