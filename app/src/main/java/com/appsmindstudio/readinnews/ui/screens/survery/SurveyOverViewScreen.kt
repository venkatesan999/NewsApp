package com.appsmindstudio.readinnews.ui.screens.survery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsmindstudio.readinnews.R
import com.appsmindstudio.readinnews.ui.components.Fonts
import com.appsmindstudio.readinnews.ui.screens.utils.OnBackPressedCall

@Composable
fun SurveyOverViewScreen(name: String?, country: String?, onNewsScreen: () -> Unit) {
    OnBackPressedCall()

    Surface(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF1B1717)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Hi, $name",
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = Fonts.mediumFontFamily,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Ready to read the top business headlines in $country right now",
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = Fonts.mediumFontFamily,
                    fontSize = 20.sp,
                    color = Color.White
                )
                ReadNewsButtonComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp)
                        .clickable { onNewsScreen() }
                        .border(0.7.dp, Color(0xFFFFFFFF), shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp))
            }
        }
    }
}

@Composable
fun ReadNewsButtonComponent(modifier: Modifier) {
    Box(
        modifier,
        contentAlignment = Alignment.Center // Center align the container
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Read news",
                textAlign = TextAlign.Center,
                color = Color.White,
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
