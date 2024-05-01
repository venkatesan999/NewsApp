package com.appsmindstudio.readinnews.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.appsmindstudio.readinnews.R
import com.appsmindstudio.readinnews.ui.components.Fonts
import com.appsmindstudio.readinnews.ui.theme.NewsInShortTheme
import com.appsmindstudio.readinnews.util.SharedPreferencesUtil
import com.appsmindstudio.readinnews.util.StaticOnBoardList.staticOnBoardList

class OnBoardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NewsInShortTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.align(Alignment.TopCenter),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 80.dp, bottom = 20.dp)
                                    .fillMaxWidth(),
                                text = "Welcome to ReadInNews!",
                                fontFamily = Fonts.mediumFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                            OnBoardComponent()
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(50.dp)
                                .clickable {
                                    SharedPreferencesUtil.setBoolean(
                                        context = this@OnBoardActivity,
                                        key = "isOnBoardScreenEnabled",
                                        value = true
                                    )
                                    val intent =
                                        Intent(this@OnBoardActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                .border(
                                    1.dp,
                                    Color(0xFFEFEEF2),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Quicken",
                                    textAlign = TextAlign.Center,
                                    color = colorResource(id = R.color.black),
                                    fontFamily = Fonts.mediumFontFamily,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // Add space between text and image
                                Image(
                                    painter = painterResource(id = R.drawable.arrow),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    colorFilter = ColorFilter.tint(colorResource(id = R.color.black))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OnBoardComponent() {
    LazyRow(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {// This is for setting the spacing between each item.
            Spacer(modifier = Modifier.width(25.dp))
        }
        items(staticOnBoardList.size) { index ->
            staticOnBoardList[index].Image?.let { OnboardComponent(it) }
        }
        item {
            Spacer(modifier = Modifier.width(25.dp))
        }
    }
}

@Composable
fun OnboardComponent(@DrawableRes image: Int) {
    Card(
        modifier = Modifier
            .height(420.dp)
            .width(250.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )
    }
}
