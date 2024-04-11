package com.appsmindstudio.newsinshort.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsmindstudio.newsinshort.data.AppConstants
import com.appsmindstudio.newsinshort.data.entity.NewsResponse
import com.appsmindstudio.newsinshort.data.repository.NewsRepository
import com.appsmindstudio.newsinshort.util.AppConstants.NO_INTERNET
import com.appsmindstudio.newsinshort.util.ResourceState
import com.appsmindstudio.newsinshort.util.connectivity.ConnectivityObserver
import com.appsmindstudio.newsinshort.util.connectivity.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
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

    private val _newsHeadlines: MutableStateFlow<ResourceState<NewsResponse?>> =
        MutableStateFlow(ResourceState.Loading())

    val news: StateFlow<ResourceState<NewsResponse?>> = _newsHeadlines

    fun getNews(isInternetConnected: ConnectivityObserver.Status) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isInternetConnected == ConnectivityObserver.Status.Available) {
                newsRepository.getNewsHeadlines(AppConstants.COUNTRY)
                    .collectLatest { newsResponse ->
                        _newsHeadlines.value = newsResponse
                    }
            } else {
                _newsHeadlines.value =
                    ResourceState.Error(NO_INTERNET)
            }
        }
    }
}