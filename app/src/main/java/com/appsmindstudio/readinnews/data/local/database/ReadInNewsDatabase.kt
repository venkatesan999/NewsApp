package com.appsmindstudio.readinnews.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.appsmindstudio.readinnews.data.local.dao.SurveyDao
import com.appsmindstudio.readinnews.data.local.entity.SurveyEntity

@Database(entities = [SurveyEntity::class], version = 1)
abstract class ReadInNewsDatabase : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao

    companion object {
        const val DATABASE_NAME = "readinnews_db"

        val MIGRATION_0_1 = object : Migration(0, 1) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS survey_database " +
                            "(userId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "surveyDate TEXT, " +
                            "name TEXT, " +
                            "country TEXT, " +
                            "countryName TEXT)"
                )
            }
        }
    }
}
