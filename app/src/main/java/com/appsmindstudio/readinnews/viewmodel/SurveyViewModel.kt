package com.appsmindstudio.readinnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsmindstudio.readinnews.data.room.SurveyEntity
import com.appsmindstudio.readinnews.data.repository.SurveyRepository
import com.appsmindstudio.readinnews.util.getCurrentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val repository: SurveyRepository
) : ViewModel() {

    val getAllSurvey: LiveData<List<SurveyEntity>> = repository.getAllSurvey
    fun insertSurvey(name: String, countryCode: String, countryName: String, category: String) =
        viewModelScope.launch {
            repository.insert(
                SurveyEntity(
                    surveyDate = getCurrentDateTime(),
                    name = name,
                    country = countryCode,
                    countryName = countryName,
                    category = category
                )
            )
        }
}