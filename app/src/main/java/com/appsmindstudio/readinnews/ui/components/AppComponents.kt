package com.appsmindstudio.readinnews.ui.components

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.appsmindstudio.readinnews.R
import com.appsmindstudio.readinnews.data.api.api_models.Article
import com.appsmindstudio.readinnews.ui.components.Fonts.mediumFontFamily
import com.appsmindstudio.readinnews.ui.components.Fonts.regularFontFamily
import com.appsmindstudio.readinnews.ui.navigation.Destinations
import com.appsmindstudio.readinnews.util.AppConstants
import com.appsmindstudio.readinnews.util.MMM_dd
import com.appsmindstudio.readinnews.util.convertToDateFormat
import com.appsmindstudio.readinnews.util.yyyy_MM_dd_T_HH_mm_ss

object Fonts {
    val regularFontFamily = FontFamily(Font(R.font.jost_regular))
    val mediumFontFamily = FontFamily(Font(R.font.jost_medium))
    val semiBoldFontFamily = FontFamily(Font(R.font.jost_semi_bold))
}

@Composable
fun Loader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(80.dp)
                .padding(18.dp),
            color = Color.Gray
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CustomSnackBarComponent(
    isInternetConnected: Boolean = true,
    snackBarMessage: String,
    snackBarHostState: SnackbarHostState,
    onActionClick: () -> Unit
) {
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) { snackBarData ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .background(Color.Black, RoundedCornerShape(5.dp))
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = snackBarData.visuals.message,
                    style = TextStyle(color = Color.White, fontFamily = regularFontFamily),
                    textAlign = TextAlign.Start
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .weight(1f)
                )
                if (!snackBarData.visuals.actionLabel.isNullOrEmpty()) {
                    Text(
                        text = snackBarData.visuals.actionLabel!!,
                        modifier = Modifier
                            .clickable {
                                snackBarData.performAction()
                            }
                            .padding(end = 10.dp),
                        style = TextStyle(
                            color = Color.Red,
                            fontFamily = regularFontFamily
                        ),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }) {
        LaunchedEffect(Unit) {
            while (true) {
                val result = snackBarHostState.showSnackbar(
                    message = snackBarMessage,
                    actionLabel = if (isInternetConnected) "Dismiss" else "Retry",
                    duration = SnackbarDuration.Indefinite
                )
                if (result == SnackbarResult.ActionPerformed && snackBarMessage == AppConstants.NO_INTERNET)
                    onActionClick()

                if (isInternetConnected)
                    break // Break the loop if internet is connected

            }
        }
    }
}

@Composable
fun MediumFontTextComponent(textValue: String, centerAlign: Boolean = false) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = textValue,
        fontFamily = mediumFontFamily,
        fontSize = 14.sp,
        textAlign = if (centerAlign) TextAlign.Center else TextAlign.Start
    )
}

@Composable
fun RegularFontTextComponent(textValue: String, centerAlign: Boolean = false) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = textValue,
        fontFamily = regularFontFamily,
        fontSize = 16.sp,
        textAlign = if (centerAlign) TextAlign.Center else TextAlign.Start
    )
}

@Composable
fun SemiBoldFontTextComponent(textValue: String, centerAlign: Boolean = false) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = textValue,
        fontFamily = Fonts.semiBoldFontFamily,
        fontSize = 20.sp,
        textAlign = if (centerAlign) TextAlign.Center else TextAlign.Start
    )
}

@Composable
fun NewsColumnComponent(article: Article) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            model = article.urlToImage,
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            placeholder = painterResource(id = R.drawable.picture),
            error = painterResource(id = R.drawable.picture)
        )
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            MediumFontTextComponent(
                textValue = "Publish at: ${
                    article.publishedAt.convertToDateFormat(
                        yyyy_MM_dd_T_HH_mm_ss,
                        MMM_dd
                    )
                }"
            )
            Spacer(modifier = Modifier.height(10.dp))
            SourceTextComponent(article.source?.name)
            Spacer(modifier = Modifier.height(10.dp))
            SemiBoldFontTextComponent(textValue = article.title ?: "")
            Spacer(modifier = Modifier.height(10.dp))
            RegularFontTextComponent(textValue = article.description ?: "")
            Spacer(modifier = Modifier.height(10.dp))
            ReadMoreButtonComponent(if (article.url.isNullOrEmpty()) "" else article.url)
            Spacer(modifier = Modifier.weight(1f))
            SpanTextComponent(article.author, if (article.author.isNullOrEmpty()) "" else "Author:")
        }
    }
}

@Composable
fun SourceTextComponent(textValue: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.news_report),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 5.dp)
                .size(24.dp)
                .align(Alignment.CenterVertically)
        )
        textValue?.let { RegularFontTextComponent(textValue = textValue) }
    }

}

@Composable
fun ReadMoreButtonComponent(url: String?) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
                Log.d("Read more", url.toString())
            }
            .border(0.7.dp, Color(0xFFEFEEF2), shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Read more",
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.button_color),
                fontFamily = mediumFontFamily
            )
            Spacer(modifier = Modifier.width(8.dp)) // Add space between text and image
            Image(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(color = colorResource(id = R.color.button_color))
            )
        }
    }
}

@Composable
fun SpanTextComponent(name: String?, spanText: String) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray)) {
            append(spanText)
        }
        if (!name.isNullOrEmpty()) append("$name")
    }
    Text(
        fontFamily = regularFontFamily,
        fontSize = 16.sp,
        text = annotatedString,
        textAlign = TextAlign.Start
    )
}

@Composable
fun EmptyStateComponent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.no_data), contentDescription = "")
        RegularFontTextComponent(
            textValue = "No News as of now \nPlease check in some time!",
            centerAlign = true
        )
    }
}

@Composable
fun DragComponent(navController: NavHostController) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var isVisible by rememberSaveable { mutableStateOf(true) }


    val d = LocalDensity.current

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            isVisible = destination.route == Destinations.NEWS_SCREEN.name
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 10.dp, bottom = 50.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset((offsetX / d.density).dp, (offsetY / d.density).dp)
                    .background(Color.Black, shape = RoundedCornerShape(50.dp))
                    .size(115.dp, 45.dp)
                    .align(Alignment.BottomEnd) // Align the Box to the bottom end
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        }
                    }
                    .clickable {
                        navController.navigate(Destinations.SURVEY_HISTORY_SCREEN.name)
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.survey_history),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "S..Hist", fontFamily = regularFontFamily, color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsRowComponentPreview() {
    val article = Article("Top Headlines", "News Reader", "XYZ")
    NewsColumnComponent(article = article)
}
