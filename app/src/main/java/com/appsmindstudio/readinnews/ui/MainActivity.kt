package com.appsmindstudio.readinnews.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.appsmindstudio.readinnews.data.local.entity.SurveyEntity
import com.appsmindstudio.readinnews.ui.components.DragComponent
import com.appsmindstudio.readinnews.ui.navigation.AppNavigation
import com.appsmindstudio.readinnews.ui.theme.NewsInShortTheme
import com.appsmindstudio.readinnews.viewmodel.SurveyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                val navController = rememberNavController()
                NewsInShortTheme {
                    AppEntryPoint(navController)
                    DragComponent(navController)
                }
            }
        }
    }
}

@Composable
fun AppEntryPoint(navController: NavHostController) {
    val viewModel: SurveyViewModel = viewModel()
    val surveyList: List<SurveyEntity> by viewModel.getAllSurvey.observeAsState(initial = emptyList())
    AppNavigation(navController, surveyList.isNotEmpty())
}
