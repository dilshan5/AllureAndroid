package com.qa.android.allure_android.rules

import android.Manifest
import androidx.test.espresso.Espresso.setFailureHandler
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * [TestRule] that captures a screenshot when a test fails and stores it at [screenshotRelativePath] inside
 * `Pictures/screenshots` directory on the external storage.
 *
 * The following permissions are required to access files on external storage.
 * - [Manifest.permission.WRITE_EXTERNAL_STORAGE]
 * - [Manifest.permission.READ_EXTERNAL_STORAGE]
 * These permissions should be managed by the consumer of this [TestRule]. Check [ScreenShotRuleChain] for reference.
 *
 * @param screenshotRelativePath Relative path of the directory where screen shots have to be stored.
 */
class ScreenshotRule(
    private val screenshotRelativePath: String
) : TestRule {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                setFailureHandler(ScreenshotFailureHandler(context, description, screenshotRelativePath))
                base.evaluate()
            }
        }
    }
}

/**
 * [RuleChain] that wraps [GrantPermissionRule] and [ScreenshotRule] together in that order. Runtime permissions are
 * required to store files processed by [ScreenshotRule].
 */
@Suppress("FunctionName") // factory method
fun ScreenShotRuleChain(
    screenshotRelativePath: String
): RuleChain {
    val screenshotRule = ScreenshotRule(screenshotRelativePath)

    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule
        .grant(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    return RuleChain.outerRule(runtimePermissionRule)
        .around(screenshotRule)
}