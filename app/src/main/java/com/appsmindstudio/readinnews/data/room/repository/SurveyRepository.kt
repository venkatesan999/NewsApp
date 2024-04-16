package com.appsmindstudio.readinnews.data.room.repository

import com.appsmindstudio.readinnews.data.room.dao.SurveyDao
import com.appsmindstudio.readinnews.data.room.models.Survey
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SurveyRepository @Inject constructor(private val surveyDao: SurveyDao) {

    val getAllSurvey: Flow<List<Survey>> = surveyDao.getAllSurvey()
    suspend fun insert(survey: Survey) {
        surveyDao.insert(survey)
    }
}