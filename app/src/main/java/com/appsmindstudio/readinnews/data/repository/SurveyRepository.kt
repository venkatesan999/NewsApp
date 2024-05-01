package com.appsmindstudio.readinnews.data.repository

import androidx.lifecycle.LiveData
import com.appsmindstudio.readinnews.data.room.SurveyDao
import com.appsmindstudio.readinnews.data.room.SurveyEntity
import javax.inject.Inject

class SurveyRepository @Inject constructor(private val surveyDao: SurveyDao) {

    val getAllSurvey: LiveData<List<SurveyEntity>> = surveyDao.getAllSurvey()
    suspend fun insert(survey: SurveyEntity) {
        surveyDao.insert(survey)
    }
}