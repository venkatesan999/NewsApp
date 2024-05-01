package com.appsmindstudio.readinnews.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SurveyEntity::class], version = 1)
abstract class ReadInNewsDatabase : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao

    companion object {
        const val DATABASE_NAME = "survey_list"
    }
}
