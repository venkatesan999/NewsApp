package com.appsmindstudio.readinnews.util.connectivity

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val MERCHANT_DATASTORE = "merchant_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = MERCHANT_DATASTORE)

class DataSr(val context: Context) {

    companion object {
        val COUNTRY = stringPreferencesKey("COUNTRY")
    }

    suspend fun saveToDataStore(countryName: String) {
        context.dataStore.edit {
            it[COUNTRY] = countryName
        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        it[COUNTRY] ?: ""
    }

    suspend fun clearDataStore() = context.dataStore.edit {
        it.clear()
    }

}