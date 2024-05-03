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

    fun setUserName(context: Context, key: String?, value: String) {
        getSharedPreferences(context).edit().putString(key, value).apply()
    }

    fun getUserName(context: Context, key: String): String? {
        return getSharedPreferences(context).getString(key, "")
    }
    fun setCodeCategories(context: Context, countryCode: String, countryCodeValue: String, category: String, categoryValue: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(countryCode, countryCodeValue)
        editor.putString(category, categoryValue)
        editor.apply()
    }

    fun getCodeCategories(context: Context, key1: String, key2: String): Pair<String?, String?> {
        val sharedPreferences = getSharedPreferences(context)
        val value1 = sharedPreferences.getString(key1, "")
        val value2 = sharedPreferences.getString(key2, "")
        return Pair(value1, value2)
    }

    fun setSwipeUp(context: Context, key: String, value: Boolean) {
        getSharedPreferences(context).edit().putBoolean(key, value).apply()
    }

    fun getSwipeUp(context: Context, key: String): Boolean {
        return getSharedPreferences(context).getBoolean(key, false)
    }

}
