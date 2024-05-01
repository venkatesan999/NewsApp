package com.appsmindstudio.readinnews.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtil {
    private const val PREFERENCES_NAME = "my_preferences"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setBoolean(context: Context, key: String, value: Boolean) {
        getSharedPreferences(context).edit().putBoolean(key, value).apply()
    }

    fun getBoolean(context: Context, key: String): Boolean {
        return getSharedPreferences(context).getBoolean(key, false)
    }

    fun setUserName(context: Context, key: String?,  value: String) {
        getSharedPreferences(context).edit().putString(key, value).apply()
    }

    fun getUserName(context: Context, key: String): String? {
        return getSharedPreferences(context).getString(key, "")
    }
}
