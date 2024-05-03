package com.appsmindstudio.readinnews.ui.screens.news

import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appsmindstudio.readinnews.R
import com.appsmindstudio.readinnews.data.models.NewsResponse
import com.appsmindstudio.readinnews.ui.components.CustomSnackBarComponent
import com.appsmindstudio.readinnews.ui.components.EmptyStateComponent
import com.appsmindstudio.readinnews.ui.components.Loader
import com.appsmindstudio.readinnews.ui.components.LottieAnimation
import com.appsmindstudio.readinnews.ui.components.NewsColumnComponent
import com.appsmindstudio.readinnews.ui.utils.OnBackPressedCall
import com.appsmindstudio.readinnews.util.AppConstants
import com.appsmindstudio.readinnews.util.ResourceState
import com.appsmindstudio.readinnews.util.SharedPreferencesUtil
import com.appsmindstudio.readinnews.util.SharedPreferencesUtil.getSwipeUp
import com.appsmindstudio.readinnews.util.SharedPreferencesUtil.setSwipeUp
import com.appsmindstudio.readinnews.util.connectivity.ConnectivityObserver
import com.appsmindstudio.readinnews.viewmodel.NewsViewModel

const val TAG = "HomeScreen"

@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {

    OnBackPressedCall()

    val context = LocalContext.current
    val (countryCode, category) = SharedPreferencesUtil.getCodeCategories(
        context,
        "countryCode",
        "category"
    )

    val status by newsViewModel.connectivityObserver.observe()
        .collectAsState(initial = ConnectivityObserver.Status.Unavailable)

    if (countryCode?.isNotEmpty() == true && category?.isNotEmpty() == true) {
        newsViewModel.getNews(status, countryCode.lowercase(), category.lowercase())
    }

    val newsRes by newsViewModel.news.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    Surface(modifier = Modifier.fillMaxSize()) {
        when (val result = newsRes) {
            is ResourceState.Loading -> Loader()
            is ResourceState.Success -> result.data?.let { NewsContent(context, it) }
            is ResourceState.Error -> {
                val error = result.error
                CustomSnackBarComponent(
                    error != AppConstants.NO_INTERNET,
                    snackBarMessage = error,
                    snackBarHostState = snackBarHostState
                ) {
                    if (countryCode?.isNotEmpty() == true && category?.isNotEmpty() == true) {
                        newsViewModel.getNews(status, countryCode, category)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsContent(context: Context, response: NewsResponse) {

    val pagerState = rememberPagerState(pageCount = { response.articles.size })

    if (!getSwipeUp(context, "swipe_up")) {
        LaunchedEffect(pagerState.currentPage) {
            setSwipeUp(context, "swipe_up", true)
        }
    }

    if (response.articles.isEmpty()) {
        EmptyStateComponent()
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                pageSize = PageSize.Fill,
                pageSpacing = (-50).dp
            ) { page: Int ->
                val article = response.articles.getOrNull(page)
                if (article != null) NewsColumnComponent(article)
            }
            if (!getSwipeUp(context, "swipe_up")) {
                LottieAnimation(
                    context, R.raw.swipe_up, modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 30.dp),
                    color = Color.Black
                )
            }
        }

        Log.d(TAG, "Success: ${response.status} = ${response.totalResults}")
    }
}

