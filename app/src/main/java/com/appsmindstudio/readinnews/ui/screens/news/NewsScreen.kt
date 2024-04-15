package com.appsmindstudio.readinnews.ui.screens.news

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appsmindstudio.readinnews.ui.components.CustomSnackBarComponent
import com.appsmindstudio.readinnews.ui.components.EmptyStateComponent
import com.appsmindstudio.readinnews.ui.components.Loader
import com.appsmindstudio.readinnews.ui.components.NewsColumnComponent
import com.appsmindstudio.readinnews.ui.screens.OnBackPressedCall
import com.appsmindstudio.readinnews.util.AppConstants
import com.appsmindstudio.readinnews.util.ResourceState
import com.appsmindstudio.readinnews.util.connectivity.ConnectivityObserver
import com.appsmindstudio.readinnews.viewmodel.NewsViewModel

const val TAG = "HomeScreen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel(),
    country: String
) {

    OnBackPressedCall()

    val status by newsViewModel.connectivityObserver.observe()
        .collectAsState(initial = ConnectivityObserver.Status.Unavailable)

    if (country.isNotEmpty())
        newsViewModel.getNews(status, country)

    val newsRes by newsViewModel.news.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (newsRes) {

            is ResourceState.Loading -> {
                Log.d(TAG, "Loading")
                Loader()
            }

            is ResourceState.Success -> {

                val response = (newsRes as ResourceState.Success).data

                val pagerState = rememberPagerState(pageCount = { response?.articles?.size ?: 1 })

                if (response?.articles?.isEmpty() == true)
                    EmptyStateComponent()
                else VerticalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    pageSize = PageSize.Fill,
                    contentPadding = PaddingValues(10.dp),
                    pageSpacing = 10.dp // Reduced page spacing for better alignment
                ) { page: Int ->

                    val article = response?.articles?.getOrNull(page)

                    if (article != null) NewsColumnComponent(
                        article
                    )

                }

                Log.d(TAG, "Success: ${response?.status} = ${response?.totalResults}")

            }

            is ResourceState.Error -> {

                val error = (newsRes as ResourceState.Error).error

                CustomSnackBarComponent(
                    error != AppConstants.NO_INTERNET,
                    snackBarMessage = error,
                    snackBarHostState = snackBarHostState
                ) {
                    if (country.isNotEmpty())
                        newsViewModel.getNews(status, country)
                }

                Log.d(TAG, "Error: $error")
            }
        }
    }
}
