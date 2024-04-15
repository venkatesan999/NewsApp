package com.appsmindstudio.readinnews.data.local.preferences

import androidx.datastore.preferences.core.stringPreferencesKey

object AppPreferencesKeys {
    val name = stringPreferencesKey("name")
    val country = stringPreferencesKey("country")
    val countryCode = stringPreferencesKey("countryCode")

}

