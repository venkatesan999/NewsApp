package com.appsmindstudio.newsinshort.data.repository

import com.appsmindstudio.newsinshort.data.datasource.NewsDataSource
import com.appsmindstudio.newsinshort.data.entity.NewsResponse
import com.appsmindstudio.newsinshort.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsDataSource: NewsDataSource) {

    suspend fun getNewsHeadlines(country: String): Flow<ResourceState<NewsResponse?>> {
        return flow {
            emit(ResourceState.Loading())
            val response = newsDataSource.getNewsHeadline(country)
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