package com.appsmindstudio.readinnews.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.appsmindstudio.readinnews.ui.navigation.AppNavigation
import com.appsmindstudio.readinnews.ui.theme.NewsInShortTheme
import com.appsmindstudio.readinnews.util.SharedPreferencesUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                val navController = rememberNavController()
                NewsInShortTheme {
                    val isOnBoardScreenEnabled = SharedPreferencesUtil.getBoolean(
                        context = this@MainActivity,
                        key = "isOnBoardScreenEnabled"
                    )
                    if (isOnBoardScreenEnabled) {
                        AppEntryPoint(navController)
                    } else {
                        val intent =
                            Intent(this@MainActivity, OnBoardActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun AppEntryPoint(navController: NavHostController) {
    val context = LocalContext.current
    val userName = SharedPreferencesUtil.getUserName(
        context = context,
        key = "userName"
    )
    AppNavigation(context, navController, userName.isNullOrEmpty())
}
