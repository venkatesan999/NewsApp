package com.appsmindstudio.readinnews.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SurveyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(survey: SurveyEntity): Long
    @Query("SELECT * FROM survey_list")
    fun getAllSurvey(): LiveData<List<SurveyEntity>>
}