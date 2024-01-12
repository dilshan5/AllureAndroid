package com.qa.android.allure_android.test_data

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import java.time.LocalDateTime
import com.qa.android.allure_android.dataset.testing.R as DatasetR

val testContext: Context? = InstrumentationRegistry.getInstrumentation().context
data class ContactData(
    val name: String = "User "+ LocalDateTime.now(),
    val accountName: String = "Savings "+ LocalDateTime.now(),
    val accountNumber: String = "CH35 3412 4149 2649 1635 5"
)
object ContactDataProvider {

    val swiftContactDetails =
        testContext?.let {
            ContactData(
                name = it.getString(DatasetR.string.td_swiftContactName),
                accountName = it.getString(DatasetR.string.td_swiftContactAccountNumber),
                accountNumber = "CH6789144641217388936"
            )
        }
}
