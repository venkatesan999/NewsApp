package com.appsmindstudio.readinnews.ui.screens.news

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appsmindstudio.readinnews.R
import com.appsmindstudio.readinnews.data.local.preferences.pref_manager.AppPreferencesManager
import com.appsmindstudio.readinnews.data.room.models.Survey
import com.appsmindstudio.readinnews.ui.components.Fonts
import com.appsmindstudio.readinnews.viewmodel.SharedViewModel
import com.appsmindstudio.readinnews.viewmodel.SurveyViewModel

@Composable
fun SurveyHistoryScreen(
    navigateToNewsScreen: (name: String?, countryName: String?, countryCode: String?) -> Unit,
    navigateToSurveyScreen: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: SurveyViewModel = hiltViewModel()
) {

    val viewModels: SharedViewModel = viewModel()
    val appPreferencesManager = AppPreferencesManager(context = LocalContext.current)
    val countryName = viewModels.getCountryCode(appPreferencesManager)

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp), contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.clickable { navigateBack() },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        alignment = Alignment.CenterStart
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Go back",
                        fontFamily = Fonts.semiBoldFontFamily,
                        fontSize = 16.sp // Let the text take up remaining space
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Hi,",
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = Fonts.mediumFontFamily,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "This is your ReadInNews survey history",
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = Fonts.mediumFontFamily,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    val surveyList: List<Survey> by viewModel.getAllSurvey.observeAsState(
                        initial = emptyList()
                    )
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(surveyList) { survey ->
                            Spacer(modifier = Modifier.height(15.dp))
                            SurveyItem(countryName, survey, onClick = { clickedSurvey ->
                                navigateToNewsScreen(
                                    clickedSurvey.name,
                                    clickedSurvey.countryName,
                                    clickedSurvey.country
                                )
                            })

                        }
                    }
                    TakeSurveyButtonComponent(navigateToSurveyScreen)
                }
            }
        }

    }
}

@Composable
fun TakeSurveyButtonComponent(navigateToSurveyScreen: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp)
            .clickable { navigateToSurveyScreen() }
            .border(0.7.dp, Color(0xFFEFEEF2), shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Take survey",
                textAlign = TextAlign.Center,
                color = Color(0xFF2602AE),
                fontFamily = Fonts.mediumFontFamily,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(8.dp)) // Add space between text and image
            Image(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color(0xFF2602AE))
            )
        }
    }
}

@Composable
fun SurveyItem(countryName: String, survey: Survey, onClick: (Survey) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(survey) },
        shape = RoundedCornerShape(10.dp),
        colors = CardColors(
            containerColor = Color(0x30D9D9D9),
            contentColor = Color(0xFF000000),
            disabledContainerColor = Color(0x30D9D9D9),
            disabledContentColor = Color(0xFF000000)
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp) // Add padding to the Column
        ) {
            Row {
                Image(
                    painter = painterResource(id = if (countryName == survey.country) R.drawable.live else R.drawable.news_report),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "${survey.country}",
                    fontFamily = Fonts.semiBoldFontFamily,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.weight(1f)) // Take up remaining space
                Text(
                    text = "${survey.surveyDate}",
                    textAlign = TextAlign.End,
                    fontFamily = Fonts.mediumFontFamily,
                    color = Color(0x50000000),
                    fontSize = 12.sp
                )
            }
            TextComponent("Your name: ${survey.name}")
            TextComponent("Selected country: ${survey.countryName}")
        }
    }
}

@Composable
fun TextComponent(value: String) {
    Text(
        text = value,
        fontFamily = Fonts.regularFontFamily,
        fontSize = 16.sp,
        modifier = Modifier.padding(start = 25.dp)
    )
}
