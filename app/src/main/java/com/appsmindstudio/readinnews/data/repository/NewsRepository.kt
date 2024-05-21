package com.appsmindstudio.readinnews.data.repository

import com.appsmindstudio.readinnews.data.api.datasource.NewsDataSource
import com.appsmindstudio.readinnews.data.models.NewsResponse
import com.appsmindstudio.readinnews.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsDataSource: NewsDataSource) {
    suspend fun getNewsHeadlines(
        country: String,
        category: String
    ): Flow<ResourceState<NewsResponse?>> {
        return flow {
            emit(ResourceState.Loading())
            val response = newsDataSource.getNewsHeadline(country, category)
            if (response.isSuccessful && response.body() != null)
                emit(ResourceState.Success(response.body()))
            else emit(ResourceState.Error("Error fetching news data"))
        }.catch { error ->
            emit(
                ResourceState.Error(
                    error.localizedMessage ?: "Some error in flow"
                )
            )
        }
    }
}