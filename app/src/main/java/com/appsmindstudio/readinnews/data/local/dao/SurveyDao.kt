package com.appsmindstudio.readinnews.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appsmindstudio.readinnews.data.local.entity.SurveyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SurveyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(surveyEntity: SurveyEntity): Long

    @Query("SELECT * FROM survey_database")
    fun getAllSurvey(): Flow<List<SurveyEntity>>
}