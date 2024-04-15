package com.appsmindstudio.readinnews.ui.entity

import android.media.Image

data class SurveyHistoryModel(
    val surveyHistory: List<ReadInNewsSurvey> = emptyList()
)

data class ReadInNewsSurvey(
    val country: String? = null,
    val date: String? = null,
    val name: String? = null
)