package com.appsmindstudio.newsinshort.data.datasource

import com.appsmindstudio.newsinshort.data.entity.NewsResponse
import retrofit2.Response

interface NewsDataSource {

    suspend fun getNewsHeadline(country: String): Response<NewsResponse>
}