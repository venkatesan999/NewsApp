package com.appsmindstudio.readinnews.data.repository

import androidx.lifecycle.LiveData
import com.appsmindstudio.readinnews.data.local.dao.SurveyDao
import com.appsmindstudio.readinnews.data.local.entity.SurveyEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SurveyRepository @Inject constructor(private val surveyDao: SurveyDao) {

    val getAllSurvey: Flow<List<SurveyEntity>> = surveyDao.getAllSurvey()
    suspend fun insert(surveyEntity: SurveyEntity) {
        surveyDao.insert(surveyEntity)
    }
}