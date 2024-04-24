package com.appsmindstudio.readinnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsmindstudio.readinnews.data.local.preferences.pref_manager.AppPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private lateinit var appPreferencesManager: AppPreferencesManager

    private var getCountryCode: String = ""
    private var getCategory: String = ""

    fun getCountryCode(manager: AppPreferencesManager): String {
        appPreferencesManager = manager
        viewModelScope.launch {
            appPreferencesManager.preferencesFlow.collect { appPreferencesModel ->
                getCountryCode = appPreferencesModel.countryCode
            }
        }
        return getCountryCode
    }
 fun getCategory(manager: AppPreferencesManager): String {
        appPreferencesManager = manager
        viewModelScope.launch {
            appPreferencesManager.preferencesFlow.collect { appPreferencesModel ->
                getCategory = appPreferencesModel.category
            }
        }
        return getCategory
    }

    fun getName(manager: AppPreferencesManager): String {
        var name = ""
        appPreferencesManager = manager
        viewModelScope.launch {
            appPreferencesManager.preferencesFlow.collect { appPreferencesModel ->
                name = appPreferencesModel.name
            }
        }
        return name
    }

    fun updateCountry(
        name: String?,
        country: String?,
        countryCode: String?,
        category: String?,
        manager: AppPreferencesManager
    ) {
        appPreferencesManager = manager
        viewModelScope.launch {
            appPreferencesManager.updateCountry(
                name.toString(),
                country.toString(),
                countryCode.toString(),
                category.toString()
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel cleared")
    }
}