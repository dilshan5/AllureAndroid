package com.qa.android.allure_android.rules

import android.content.Context
import android.view.View
import androidx.test.espresso.FailureHandler
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.base.DefaultFailureHandler
import androidx.test.runner.screenshot.BasicScreenCaptureProcessor
import androidx.test.runner.screenshot.Screenshot
import com.qa.android.allure_android.util.Logger
import java.io.File
import java.io.IOException
import org.hamcrest.Matcher
import org.junit.runner.Description

/**
 * Implementation of [FailureHandler] that captures a screenshot in [handle] method. Internally, this class uses
 * [BasicScreenCaptureProcessor] to store [Bitmap] into a [File] inside [failureDirectoryRelativePath].
 *
 * Note:- [BasicScreenCaptureProcessor] internally uses `sdcard/Pictures/screenshots` directory to store screenshots.
 *
 * @param targetContext [Context] instance required to instantiate [DefaultFailureHandler].
 * @param description [Description] required to extract a test's demographic information.
 * @param failureDirectoryRelativePath Relative path of the directory where screenshots should be saved.
 */
internal class ScreenshotFailureHandler(
    targetContext: Context,
    description: Description,
    private val failureDirectoryRelativePath: String,
) : FailureHandler {

    private val delegate: FailureHandler
    private val testClassName = description.className
    private val testMethodName = description.methodName

    init {
        delegate = DefaultFailureHandler(targetContext)
    }

    override fun handle(error: Throwable, viewMatcher: Matcher<View>) {
        try {
            val testFolderRelativePath = "$failureDirectoryRelativePath/$testClassName"
            processScreenshot(testFolderRelativePath, testMethodName)

            delegate.handle(error, viewMatcher)

        } catch (exception: NoMatchingViewException) {
            throw exception
        }
    }

    private fun processScreenshot(testFolderRelativePath: String, screenshotName: String) {
        Logger.debug(SCREENSHOT_TAG, "Taking screenshot of '$screenshotName'")
        val screenCapture = Screenshot.capture()
        val processors = setOf(TestScreenCaptureProcessor(testFolderRelativePath))
        try {
            screenCapture.apply {
                name = screenshotName
                process(processors)
            }
            Logger.debug(SCREENSHOT_TAG, "Screenshot taken")

        } catch (exception: IOException) {
            Logger.error(SCREENSHOT_TAG, exception, "Could not take a screenshot.")
        }
    }

    private class TestScreenCaptureProcessor(testFolderRelativePath: String) : BasicScreenCaptureProcessor() {
        init {
            this.mDefaultScreenshotPath = File(mDefaultScreenshotPath, testFolderRelativePath)
        }

        override fun getFilename(prefix: String): String = prefix
    }

    private companion object {
        private val SCREENSHOT_TAG = ScreenshotFailureHandler::class.simpleName ?: "ScreenShots"
    }
}