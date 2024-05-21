package com.appsmindstudio.readinnews.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.appsmindstudio.readinnews.ui.screens.news.NewsScreen
import com.appsmindstudio.readinnews.ui.screens.news.SurveyHistoryScreen
import com.appsmindstudio.readinnews.ui.screens.survey.SurveyOverViewScreen
import com.appsmindstudio.readinnews.ui.screens.survey.SurveyScreen
import com.appsmindstudio.readinnews.util.SharedPreferencesUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


enum class Destinations {
    SURVEY_SCREEN, SURVEY_OVERVIEW_SCREEN, NEWS_SCREEN, SURVEY_HISTORY_SCREEN, SURVEY_GRAPH, NEWS_GRAPH
}

@Composable
fun AppNavigation(
    context: Context,
    navController: NavHostController,
    isAlreadySurveyTaken: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    NavHost(
        navController,
        if (isAlreadySurveyTaken) Destinations.SURVEY_GRAPH.name else Destinations.NEWS_GRAPH.name
    ) {
        news(navController, context, coroutineScope)
        survey(navController, context, coroutineScope)
    }
}

fun NavGraphBuilder.survey(
    navController: NavHostController,
    context: Context,
    coroutineScope: CoroutineScope
) {
    navigation(
        startDestination = Destinations.SURVEY_SCREEN.name,
        route = Destinations.SURVEY_GRAPH.name
    ) {

        composable(Destinations.SURVEY_SCREEN.name) {
            SurveyScreen(
                navigateSurveyOverViewScreen = { name: String, country: String, countryCode: String, category: String ->
                    navController.navigate(
                        Destinations.SURVEY_OVERVIEW_SCREEN.name + "/$name/$country/$countryCode/$category"
                    )
                }
            )
        }

        composable(
            Destinations.SURVEY_OVERVIEW_SCREEN.name + "/{name}/{countryName}/{countryCode}/{category}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("countryName") { type = NavType.StringType },
                navArgument("countryCode") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType }
            )
        ) {
            val name = it.arguments?.getString("name")
            val countryName = it.arguments?.getString("countryName")
            val countryCode = it.arguments?.getString("countryCode")
            val category = it.arguments?.getString("category")
            SurveyOverViewScreen(
                name = name,
                country = countryName,
                countryCode = countryCode,
                category = category,
                navigateToNewsScreen = {
                    coroutineScope.launch {
                        updateInSharedPreference(
                            context,
                            countryCodeValue = countryCode.toString(),
                            categoryValue = category.toString()
                        )
                    }
                    navController.navigate(
                        Destinations.NEWS_GRAPH.name
                    )
                })
        }
    }
}

fun NavGraphBuilder.news(
    navController: NavHostController,
    context: Context,
    coroutineScope: CoroutineScope
) {
    navigation(
        startDestination = Destinations.NEWS_SCREEN.name,
        route = Destinations.NEWS_GRAPH.name
    ) {

        composable(Destinations.NEWS_SCREEN.name) { _ ->
            NewsScreen(navController)
        }

        composable(Destinations.SURVEY_HISTORY_SCREEN.name) {
            SurveyHistoryScreen(
                navigateToNewsScreen = { name, countryName, countryCode, category ->
                    if (name?.isNotEmpty() == true
                        && countryName?.isNotEmpty() == true
                        && category?.isNotEmpty() == true
                    ) {
                        coroutineScope.launch {
                            updateInSharedPreference(
                                context,
                                countryCodeValue = countryCode.toString(),
                                categoryValue = category.toString()
                            )
                        }
                        navController.navigate(Destinations.NEWS_SCREEN.name)
                    }
                },
                navigateToSurveyScreen = { navController.navigate(Destinations.SURVEY_SCREEN.name) },
                navigateBack = { navController.popBackStack() })
        }
    }
}

suspend fun updateInSharedPreference(
    context: Context,
    countryCodeValue: String,
    categoryValue: String
) {
    withContext(Dispatchers.IO) {
        SharedPreferencesUtil.setCodeCategories(
            context = context,
            countryCode = "countryCode",
            countryCodeValue = countryCodeValue,
            category = "category",
            categoryValue = categoryValue
        )
    }
}
