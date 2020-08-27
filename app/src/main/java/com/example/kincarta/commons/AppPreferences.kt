package com.example.kincarta.commons

import android.content.Context
import android.content.SharedPreferences

class AppPreferences (context: Context) {
    val PREFS_NAME = "kincarta-data"
    val TITLE = "title"

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var title: Boolean
        get() = prefs.getBoolean(TITLE, false)
        set(value) = prefs.edit().putBoolean(TITLE, value).apply()

    fun clearPrefs(){
        prefs.edit().clear().apply()
    }
}