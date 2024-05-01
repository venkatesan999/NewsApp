package com.appsmindstudio.readinnews.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "survey_list")
data class SurveyEntity(
    @PrimaryKey(autoGenerate = true)
    val surveyId: Long = 0L,
    val surveyDate: String? = null,
    val name: String? = null,
    @ColumnInfo(name = "countryCode") val country: String? = null,
    val countryName: String? = null,
    val category: String? = null
)
