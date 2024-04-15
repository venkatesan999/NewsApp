package com.appsmindstudio.readinnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.appsmindstudio.readinnews.data.local.entity.SurveyEntity
import com.appsmindstudio.readinnews.data.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val repository: SurveyRepository
) : ViewModel() {

    val getAllSurvey: LiveData<List<SurveyEntity>> = repository.getAllSurvey.asLiveData()

    fun insert(surveyEntity: SurveyEntity) = viewModelScope.launch {
        repository.insert(surveyEntity)
    }
}