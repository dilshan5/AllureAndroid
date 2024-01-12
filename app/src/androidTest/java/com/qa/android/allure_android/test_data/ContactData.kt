package com.qa.android.allure_android.test_data

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import java.time.LocalDateTime
import com.qa.android.allure_android.dataset.testing.R as DatasetR

val testContext: Context? = InstrumentationRegistry.getInstrumentation().context
data class ContactData(
    val name: String = "",
    val accountName: String = "",
    val accountNumber: String = ""
)
object ContactDataProvider {

    val swiftContactDetails =
        testContext?.let {
            ContactData(
                name = it.getString(DatasetR.string.td_swiftContactName),
                accountName = "Bruise",
                accountNumber = it.getString(DatasetR.string.td_swiftContactAccountNumber)
            )
        }
}
