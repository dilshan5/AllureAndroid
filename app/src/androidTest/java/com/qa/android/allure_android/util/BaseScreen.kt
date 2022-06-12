package com.qa.android.allure_android.util

import android.content.Context
import android.os.Environment
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.redundent.kotlin.xml.xml
import java.io.File
import java.io.FileWriter

open class BaseScreen {

    fun GenerateEnvironmentXml() {
        var text = "test"

        val people = xml("environment") {
            "parameter" {
                "key" {
                    -"Browser"
                }
                "value" {
                    -"Chrome"
                }
            }
            "parameter" {
                "key" {
                    -"OS"
                }
                "value" {
                    -text
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
        writer.append(people.toString())
        writer.flush()
        writer.close()

    }
}