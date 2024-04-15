package com.appsmindstudio.readinnews.data.local.preferences.pref_manager

import android.content.Context
import com.appsmindstudio.readinnews.data.local.preferences.AppPreferencesKeys
import com.appsmindstudio.readinnews.data.local.preferences.AppPreferencesModel
import com.appsmindstudio.readinnews.data.local.preferences.appDataStore
import com.appsmindstudio.readinnews.data.local.preferences.insertOrUpdate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferencesManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.appDataStore

    val preferencesFlow = dataStore.data.map { preferences ->

        AppPreferencesModel(
            name = preferences[AppPreferencesKeys.name] ?: "",
            country = preferences[AppPreferencesKeys.country] ?: "",
            countryCode = preferences[AppPreferencesKeys.countryCode] ?: "",
        )
    }

    suspend fun updateCountry(name: String, country: String, countryCode: String) {
        dataStore.insertOrUpdate(AppPreferencesKeys.name, name)
        dataStore.insertOrUpdate(AppPreferencesKeys.country, country)
        dataStore.insertOrUpdate(AppPreferencesKeys.countryCode, countryCode)
    }

}