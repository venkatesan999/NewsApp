package com.appsmindstudio.readinnews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.appsmindstudio.readinnews.data.local.preferences.pref_manager.AppPreferencesManager
import com.appsmindstudio.readinnews.ui.screens.news.NewsScreen
import com.appsmindstudio.readinnews.ui.screens.news.SurveyHistoryScreen
import com.appsmindstudio.readinnews.ui.screens.survey.SurveyOverViewScreen
import com.appsmindstudio.readinnews.ui.screens.survey.SurveyScreen
import com.appsmindstudio.readinnews.viewmodel.SharedViewModel


enum class Destinations {
    SURVEY_SCREEN, SURVEY_OVERVIEW_SCREEN, NEWS_SCREEN, SURVEY_HISTORY_SCREEN, SURVEY_GRAPH, NEWS_GRAPH
}

@Composable
fun AppNavigation(navController: NavHostController, isAlreadySurveyTaken: Boolean = false) {
    NavHost(
        navController,
        if (isAlreadySurveyTaken) Destinations.SURVEY_GRAPH.name else Destinations.NEWS_GRAPH.name
    ) {
        news(navController)
        survey(navController)
    }
}

fun NavGraphBuilder.survey(navController: NavHostController) {
    navigation(
        startDestination = Destinations.SURVEY_SCREEN.name,
        route = Destinations.SURVEY_GRAPH.name
    ) {
        composable(Destinations.SURVEY_SCREEN.name) { entry ->
            val viewModel = entry.sharedViewModel<SharedViewModel>(navController = navController)
            val appPreferencesManager = AppPreferencesManager(context = LocalContext.current)
            SurveyScreen(
                viewModel.getName(appPreferencesManager),
                navigateSurveyOverViewScreen = { name: String, country: String, countryCode: String, category: String ->
                    viewModel.updateCountry(
                        name,
                        country,
                        countryCode,
                        category,
                        appPreferencesManager
                    )
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
                    navController.navigate(
                        Destinations.NEWS_GRAPH.name
                    )
                })
        }
    }
}

fun NavGraphBuilder.news(navController: NavHostController) {
    navigation(
        startDestination = Destinations.NEWS_SCREEN.name,
        route = Destinations.NEWS_GRAPH.name
    ) {
        composable(Destinations.NEWS_SCREEN.name) { entry ->
            val viewModel = entry.sharedViewModel<SharedViewModel>(navController = navController)
            val appPreferencesManager = AppPreferencesManager(context = LocalContext.current)
            NewsScreen(
                country = viewModel.getCountryCode(appPreferencesManager),
                category = viewModel.getCategory(appPreferencesManager)
            )
        }

        composable(Destinations.SURVEY_HISTORY_SCREEN.name) { entry ->
            val viewModel = entry.sharedViewModel<SharedViewModel>(navController = navController)
            val appPreferencesManager = AppPreferencesManager(context = LocalContext.current)
            SurveyHistoryScreen(
                navigateToNewsScreen = { name, countryName, countryCode, category ->
                    if (name?.isNotEmpty() == true && countryName?.isNotEmpty() == true && category?.isNotEmpty() == true) viewModel.updateCountry(
                        name,
                        countryName,
                        countryCode,
                        category,
                        appPreferencesManager
                    )
                    navController.navigate(Destinations.NEWS_SCREEN.name)
                },
                navigateToSurveyScreen = { navController.navigate(Destinations.SURVEY_SCREEN.name) },
                navigateBack = { navController.popBackStack() })
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}