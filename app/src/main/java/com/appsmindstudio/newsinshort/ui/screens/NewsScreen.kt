package com.appsmindstudio.newsinshort.ui.screens

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
import com.appsmindstudio.newsinshort.ui.components.CustomSnackBarComponent
import com.appsmindstudio.newsinshort.ui.components.EmptyStateComponent
import com.appsmindstudio.newsinshort.ui.components.Loader
import com.appsmindstudio.newsinshort.ui.components.NewsColumnComponent
import com.appsmindstudio.newsinshort.util.AppConstants
import com.appsmindstudio.newsinshort.util.ResourceState
import com.appsmindstudio.newsinshort.util.connectivity.ConnectivityObserver
import com.appsmindstudio.newsinshort.viewmodel.NewsViewModel

const val TAG = "HomeScreen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {

    val status by newsViewModel.connectivityObserver.observe()
        .collectAsState(initial = ConnectivityObserver.Status.Unavailable)

    newsViewModel.getNews(status)

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
                    newsViewModel.getNews(status)
                }

                Log.d(TAG, "Error: $error")
            }
        }
    }
}
