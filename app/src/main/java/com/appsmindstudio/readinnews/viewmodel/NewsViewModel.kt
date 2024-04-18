package com.appsmindstudio.readinnews.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsmindstudio.readinnews.data.api.api_models.NewsResponse
import com.appsmindstudio.readinnews.data.api.repository.NewsRepository
import com.appsmindstudio.readinnews.util.AppConstants.NO_INTERNET
import com.appsmindstudio.readinnews.util.ResourceState
import com.appsmindstudio.readinnews.util.connectivity.ConnectivityObserver
import com.appsmindstudio.readinnews.util.connectivity.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    val connectivityObserver = NetworkConnectivityObserver(context)

    private var isNewsRequestInProgress = false

    private val _newsHeadlines: MutableStateFlow<ResourceState<NewsResponse?>> =
        MutableStateFlow(ResourceState.Loading())

    val news: StateFlow<ResourceState<NewsResponse?>> = _newsHeadlines

    fun getNews(isInternetConnected: ConnectivityObserver.Status, country: String) {
        if (isNewsRequestInProgress) {
            return
        }

        isNewsRequestInProgress = true

        viewModelScope.launch(Dispatchers.IO) {
            if (isInternetConnected == ConnectivityObserver.Status.Available) {
                try {
                    val newsResponseFlow = async {
                        newsRepository.getNewsHeadlines(country)
                    }

                    val newsResponse = newsResponseFlow.await()

                    newsResponse.collectLatest { resourceState ->
                        _newsHeadlines.value = resourceState
                    }
                } catch (e: Exception) {
                    _newsHeadlines.value = ResourceState.Error(e.message ?: "Unknown error")
                }
            } else {
                _newsHeadlines.value = ResourceState.Error(NO_INTERNET)
            }

            isNewsRequestInProgress = false
        }
    }
}