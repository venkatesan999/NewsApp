package com.appsmindstudio.readinnews.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "survey_database")
data class SurveyEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0L,
    val surveyDate: String? = null,
    val name: String? = null,
    val country: String? = null,
    val countryName: String? = null
)
