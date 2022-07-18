package com.qa.android.allure_android.util

import android.os.Build
import android.os.Environment
import com.qa.android.allure_android.rules.ScreenRecordingRuleChain
import com.qa.android.allure_android.rules.ScreenShotRuleChain
import org.junit.rules.RuleChain

private const val MODEL_BANK_FAILURES_PATH = "AllureAndroid/failures"
private val externalStorageDirectoryPath = Environment.getExternalStorageDirectory().path

private val screenshotsRelativePath =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) Environment.DIRECTORY_SCREENSHOTS else "Screenshots"

private val SCREENSHOT_FAILURE_PATH =
    "$externalStorageDirectoryPath/${Environment.DIRECTORY_PICTURES}/$screenshotsRelativePath/$MODEL_BANK_FAILURES_PATH/"

/**
 * Factory method that returns a [RuleChain] using [ScreenRecordingRuleChain] method from `journey-test` library.
 */
@Suppress("FunctionName") // factory method
fun ScreenRecordingRuleChain(): RuleChain = ScreenRecordingRuleChain(SCREENSHOT_FAILURE_PATH)

/**
 * Factory method that returns a [RuleChain] using [ScreenShotRuleChain] method from `journey-test` library.
 */
@Suppress("FunctionName") // factory method
fun ScreenShotRuleChain(): RuleChain = ScreenShotRuleChain(MODEL_BANK_FAILURES_PATH)