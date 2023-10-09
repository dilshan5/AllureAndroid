package com.qa.android.allure_android.util

import android.util.Log

object Logger {
    var logLevel = LogLevel.WARN

    fun debug(tag: String?, message: String?) {
        if (logLevel.ordinal >= LogLevel.DEBUG.ordinal) {
            Log.d(tag, message!!)
        }
    }

    fun debug(tag: String?, exception: Exception, extraMessage: String) {
        debug(tag, extraMessage + " Exception: " + exception.message)
    }

    fun debug(tag: String?, error: Error, extraMessage: String) {
        debug(tag, extraMessage + " Error: " + error.message)
    }

    fun info(tag: String?, message: String?) {
        if (logLevel.ordinal >= LogLevel.INFO.ordinal) {
            Log.i(tag, message!!)
        }
    }

    fun warning(tag: String?, message: String?) {
        if (logLevel.ordinal >= LogLevel.WARN.ordinal) {
            Log.w(tag, message!!)
        }
    }

    fun warning(tag: String?, exception: Exception, extraMessage: String) {
        warning(tag, extraMessage + " Exception: " + exception.message)
    }

    fun error(tag: String?, message: String?) {
        if (logLevel.ordinal >= LogLevel.ERROR.ordinal) {
            Log.e(tag, message ?: "")
        }
    }

    fun error(tag: String?, exception: Exception) {
        error(tag, exception.message)
    }

    fun error(tag: String?, exception: Exception, extraMessage: String) {
        error(tag, extraMessage + " Exception: " + exception.message)
    }

    fun getMessageFromException(e: Exception): String {
        return if (e.message == null) e.javaClass.name else e.message!!
    }

    enum class LogLevel {
        NONE, ERROR, WARN, INFO, DEBUG
    }
}