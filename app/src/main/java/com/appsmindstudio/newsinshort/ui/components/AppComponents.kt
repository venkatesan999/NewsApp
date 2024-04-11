package com.appsmindstudio.newsinshort.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.appsmindstudio.newsinshort.R
import com.appsmindstudio.newsinshort.data.entity.Article
import com.appsmindstudio.newsinshort.util.AppConstants
import com.appsmindstudio.newsinshort.util.MMM_dd
import com.appsmindstudio.newsinshort.util.convertToDateFormat
import com.appsmindstudio.newsinshort.util.yyyy_MM_dd_T_HH_mm_ss

object Fonts {
    val regularFontFamily = FontFamily(Font(R.font.josefin_sans_regular))
    val mediumFontFamily = FontFamily(Font(R.font.josefin_sans_medium))
    val semiBoldFontFamily = FontFamily(Font(R.font.josefin_sans_semi_bold))
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
                    style = TextStyle(color = Color.White, fontFamily = Fonts.regularFontFamily),
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
                            fontFamily = Fonts.regularFontFamily
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
            .fillMaxWidth()
            .padding(top = 10.dp, end = 15.dp),
        text = textValue,
        fontFamily = Fonts.mediumFontFamily,
        fontSize = 14.sp,
        textAlign = if (centerAlign) TextAlign.Center else TextAlign.Start
    )
}

@Composable
fun RegularFontTextComponent(textValue: String, centerAlign: Boolean = false) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        text = textValue,
        fontFamily = Fonts.regularFontFamily,
        fontSize = 16.sp,
        textAlign = if (centerAlign) TextAlign.Center else TextAlign.Start
    )
}

@Composable
fun SemiBoldFontTextComponent(textValue: String, centerAlign: Boolean = false) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        text = textValue,
        fontFamily = Fonts.semiBoldFontFamily,
        fontSize = 24.sp,
        textAlign = if (centerAlign) TextAlign.Center else TextAlign.Start
    )
}

@Composable
fun NewsColumnComponent(article: Article) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.White)
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
        MediumFontTextComponent(
            textValue = "Publish at: ${
                article.publishedAt.convertToDateFormat(
                    yyyy_MM_dd_T_HH_mm_ss,
                    MMM_dd
                )
            }"
        )
        SemiBoldFontTextComponent(textValue = article.title ?: "")
        Spacer(modifier = Modifier.height(10.dp))
        RegularFontTextComponent(textValue = article.description ?: "")
        Spacer(modifier = Modifier.weight(1f))
        AuthorDetailsComponent(article.author, article.source?.name)
    }

}

@Composable
fun AuthorDetailsComponent(authorName: String?, sourceName: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 24.dp)

    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(start = 2.dp)
        ) {
            Text(
                fontFamily = Fonts.regularFontFamily,
                fontSize = 16.sp,
                text = "Author:",
                color = Color.Gray,
                textAlign = TextAlign.Start
            )

            authorName?.also {
                Text(
                    fontFamily = Fonts.regularFontFamily,
                    fontSize = 16.sp,
                    text = it,
                    textAlign = TextAlign.Start
                )
            }
        }
        sourceName?.also {
            Text(
                fontFamily = Fonts.regularFontFamily,
                fontSize = 16.sp,
                text = it,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
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

@Preview(showBackground = true)
@Composable
fun NewsRowComponentPreview() {
    val article = Article("Top Headlines", "News Reader", "XYZ")
    NewsColumnComponent(article = article)
}
