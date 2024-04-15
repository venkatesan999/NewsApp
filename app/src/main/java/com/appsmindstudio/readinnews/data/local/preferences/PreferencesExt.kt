package com.appsmindstudio.readinnews.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

const val APP_TABLE = "country_table"

val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = APP_TABLE)

suspend fun <T> DataStore<Preferences>.insertOrUpdate(key: Preferences.Key<T>, value: T) {
    edit { preferences ->
        preferences[key] = value
    }
}
