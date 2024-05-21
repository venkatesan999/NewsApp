package com.appsmindstudio.readinnews.ui.screens.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.appsmindstudio.readinnews.data.models.NewsResponse
import com.appsmindstudio.readinnews.ui.components.CategoryList
import com.appsmindstudio.readinnews.ui.components.CustomSnackBarComponent
import com.appsmindstudio.readinnews.ui.components.DragComponent
import com.appsmindstudio.readinnews.ui.components.EmptyStateComponent
import com.appsmindstudio.readinnews.ui.components.NewsColumnComponent
import com.appsmindstudio.readinnews.ui.screens.ShimmerListItem
import com.appsmindstudio.readinnews.ui.utils.OnBackPressedCall
import com.appsmindstudio.readinnews.util.AppConstants
import com.appsmindstudio.readinnews.util.ResourceState
import com.appsmindstudio.readinnews.util.SharedPreferencesUtil
import com.appsmindstudio.readinnews.util.connectivity.ConnectivityObserver
import com.appsmindstudio.readinnews.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    navHostController: NavHostController,
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
        newsViewModel.getNews(
            status,
            countryCode.lowercase(),
            category.takeIf { it != "All" } ?: "")
    }

    val newsRes by newsViewModel.news.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                when (val result = newsRes) {
                    is ResourceState.Loading -> {
                        ShimmerListItem(
                            isLoading = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }

                    is ResourceState.Success -> result.data?.let {
                        NewsContent(it)
                        DragComponent(navController = navHostController)
                    }

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
            CategoryList(context, countryCode, category) { categoryName ->
                newsViewModel.getNews(
                    status,
                    countryCode!!.lowercase(),
                    categoryName.takeIf { it != "All" } ?: ""
                )
            }
        }
    }
}

@Composable
fun NewsContent(response: NewsResponse) {
    if (response.articles.isEmpty()) {
        EmptyStateComponent()
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(response.articles) { article ->
                    NewsColumnComponent(article)
                }
            }
        }
    }
}
