package com.qa.android.allure_android.rules

import android.Manifest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.rules.RuleChain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * [TestWatcher] implementation that invokes `adb` commands to start/stop recording when a test starts/ends.
 * Creates folders based on the running test's class name and stores recording with test method's name.
 * The following permissions are required to access files on external storage.
 * - [Manifest.permission.WRITE_EXTERNAL_STORAGE]
 * - [Manifest.permission.READ_EXTERNAL_STORAGE]
 * These permissions should be managed by the consumer of this [TestRule]. Check [ScreenRecordingRuleChain] for reference.
 *
 * @param parentDirectoryPath Absolute path of the directory where screen recording has to be stored.
 */
class ScreenRecordingRule(
    private val parentDirectoryPath: String,
) : TestWatcher() {

    private val uiAutomation = InstrumentationRegistry.getInstrumentation().uiAutomation

    override fun starting(description: Description) {
        val testClassName = description.className
        val screenRecordDirectoryPath = "$parentDirectoryPath/$testClassName"
        val screenRecordFilePath =
            "$ADB_SCREEN_RECORD $screenRecordDirectoryPath/${description.methodName}$SCREEN_RECORD_FILE_EXTENSION"

        uiAutomation.executeShellCommand("$COMMAND_MAKE_DIRECTORY -p $screenRecordDirectoryPath")
        uiAutomation.executeShellCommand(screenRecordFilePath)
    }

    override fun finished(description: Description) {
        uiAutomation.executeShellCommand(ADB_KILL_SCREEN_RECORD)
    }

    private companion object {
        const val COMMAND_MAKE_DIRECTORY = "mkdir"
        const val SCREEN_RECORD_FILE_EXTENSION = ".mp4"
        const val ADB_SCREEN_RECORD = "screenrecord"
        const val ADB_KILL_SCREEN_RECORD = "killall -INT screenrecord"
    }
}

/**
 * [RuleChain] that wraps [GrantPermissionRule] and [ScreenRecordingRule] together in that order. Runtime permissions are
 * required to store files processed by [ScreenRecordingRule].
 */
@Suppress("FunctionName") // factory method
fun ScreenRecordingRuleChain(
    screenshotDirectoryPath: String
): RuleChain {

    val screenRecordingRule = ScreenRecordingRule(
        parentDirectoryPath = screenshotDirectoryPath,
    )

    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule
        .grant(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    return RuleChain.outerRule(runtimePermissionRule)
        .around(screenRecordingRule)
}