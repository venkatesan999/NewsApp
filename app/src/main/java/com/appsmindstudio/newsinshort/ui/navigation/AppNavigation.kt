package com.appsmindstudio.newsinshort.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appsmindstudio.newsinshort.ui.screens.NewsScreen

@Composable
fun AppNavigationGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "News") {
        composable(Routes.NEWS) {
            NewsScreen()
        }
        /*navigation(
            startDestination = "login",
            route = "auth"
        ) {
            composable("login") {
                val viewModel = it.sharedViewModel<NewsViewModel>(navController = navController)
                Button(onClick = {
                    navController.navigate(Routes.NEWS) {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                }) {

                    Text(text = "Navigate to home screen")
                }
            }
            composable("register") {

            }
            composable("otp") {

            }
        }*/

        /* navigation(startDestination = "news_overview", route = "news") {
             composable("news_overview") {

             }
             composable("news_entry") {

             }
         }*/
    }
}

/*
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}*/
