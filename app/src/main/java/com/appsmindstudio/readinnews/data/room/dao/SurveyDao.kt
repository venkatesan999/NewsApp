package com.appsmindstudio.readinnews.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appsmindstudio.readinnews.data.room.models.Survey
import kotlinx.coroutines.flow.Flow

@Dao
interface SurveyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(survey: Survey): Long

    @Query("SELECT * FROM survey_list")
    fun getAllSurvey(): Flow<List<Survey>>
}