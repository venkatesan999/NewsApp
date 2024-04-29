package com.appsmindstudio.readinnews.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appsmindstudio.readinnews.data.room.dao.SurveyDao
import com.appsmindstudio.readinnews.data.room.models.Survey

@Database(entities = [Survey::class], version = 1)
abstract class ReadInNewsDatabase : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao

    companion object {
        const val DATABASE_NAME = "survey_list"
    }
}
