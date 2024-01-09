package com.qa.android.allure_android.util

import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.platform.app.InstrumentationRegistry
import org.redundent.kotlin.xml.xml
import java.io.File
import java.io.FileWriter

open class BaseScreen {

    val testContext = InstrumentationRegistry.getInstrumentation().context
    /**
     * For BUILD information : https://developer.android.com/reference/android/os/Build.html
     */
    fun GenerateEnvironmentXml() {

        val environmentDetails = xml("environment") {
            "parameter" {
                "key" {
                    -"Device Name"
                }
                "value" {
                    -Build.DEVICE.toString()
                }
            }
            "parameter" {
                "key" {
                    -"API Level"
                }
                "value" {
                    -Build.VERSION.SDK_INT.toString()
                }
            }
            "parameter" {
                "key" {
                    -"Device Manufacture"
                }
                "value" {
                    -Build.MANUFACTURER.toString()
                }
            }
        }

        val c: Context = getApplicationContext()

        Environment.getExternalStorageState()
        val root = File(c.filesDir.path, "build/allure-results")
     //   val root = File(Environment.getExternalStorageDirectory(), "/googletest/test_outputfiles/build/allure-results")
        if (!root.exists()) {
            root.mkdirs()
        }

        val gpxfile = File(root, "environment.xml")
        gpxfile.createNewFile()
        val writer = FileWriter(gpxfile)
        writer.append(environmentDetails.toString())
        writer.flush()
        writer.close()

    }
}