package com.appsmindstudio.readinnews.ui.screens.survery

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appsmindstudio.readinnews.R
import com.appsmindstudio.readinnews.ui.components.Fonts
import com.appsmindstudio.readinnews.util.Utils.countries
import com.appsmindstudio.readinnews.viewmodel.SurveyViewModel
import kotlinx.coroutines.launch

@Composable
fun SurveyScreen(
    surveyName: String,
    viewModel: SurveyViewModel = hiltViewModel(),
    onSurveyOverViewScreen: (String, String, String) -> Unit
) {
    val name = remember { mutableStateOf(surveyName) }
    val countryCode = remember { mutableStateOf("") }
    val countryName = remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Hi, Take a ReadInNews survey",
                        fontFamily = Fonts.semiBoldFontFamily,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp// Use fillMaxWidth to allow the text to take up the available width
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Image(
                        painter = painterResource(id = R.drawable.surveyor),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "What is your name",
                    fontFamily = Fonts.mediumFontFamily,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomShapeOutlinedTextField("here...", name.value) { nameValues ->
                    name.value = nameValues
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Select country",
                    fontFamily = Fonts.mediumFontFamily,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomSpinnerField(countries) { selectedCountry ->
                    countryName.value = selectedCountry.substringBeforeLast(" - ")
                    countryCode.value = selectedCountry.substringAfterLast(" - ")
                }
                Spacer(modifier = Modifier.weight(1f))
                LetsGoButtonComponent(modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
                    .clickable {
                        if (name.value.isNotEmpty() && countryName.value.isNotEmpty()) {
                            onSurveyOverViewScreen(
                                name.value,
                                countryName.value,
                                countryCode.value
                            )
                            viewModel.insertSurvey(
                                name.value,
                                countryCode.value,
                                countryName.value
                            )
                        } else {
                            coroutineScope.launch {
                                Toast
                                    .makeText(
                                        context,
                                        "Please enter both name and country",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }
                    }
                    .border(0.7.dp, Color(0xFFEFEEF2), shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp))
            }
        }
    }
}

@Composable
fun CustomShapeOutlinedTextField(
    hintText: String,
    name: String,
    isIconAdded: Boolean = false,
    nameValue: (String) -> Unit
) {
    var text by remember { mutableStateOf(name.ifEmpty { "" }) }
    Box(
        modifier = Modifier
            .background(
                color = Color(0xf4f5f6f6), // You can use Color.Gray for this
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    nameValue(it)
                },
                placeholder = {
                    Text(
                        hintText,
                        style = TextStyle(
                            fontFamily = Fonts.regularFontFamily,
                            fontSize = 16.sp
                        )
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { /* Handle Done action */ }
                ),
                modifier = Modifier.weight(1f), textStyle = TextStyle(
                    fontFamily = Fonts.regularFontFamily,
                    fontSize = 16.sp
                )
            )
            if (isIconAdded) {
                Icon(
                    painter = painterResource(id = R.drawable.drop_down), // Your icon resource
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { /* Handle icon click */ }
                        .size(24.dp) // Set the size of the icon
                )

                Spacer(modifier = Modifier.width(15.dp))
            }
        }
    }
}

@Composable
fun LetsGoButtonComponent(modifier: Modifier) {
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Lets go",
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
fun CustomSpinnerField(
    countries: List<String>,
    onCountrySelected: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clickable {
                isDropdownExpanded = !isDropdownExpanded
            }
            .background(
                color = Color(0xf4f5f6f6), // You can use Color.Gray for this
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text.ifEmpty { "Select" },
                color = if (text.isEmpty()) Color.Gray else Color.Black, // Change color to gray when hint text is shown
                fontFamily = Fonts.regularFontFamily,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 15.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.drop_down), // Your icon resource
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        isDropdownExpanded = !isDropdownExpanded
                    }
                    .size(55.dp) // Set the size of the icon
                    .padding(15.dp)
                    .align(Alignment.CenterVertically) // Align the icon to the center vertically
            )
        }
    }

    DropdownMenu(
        expanded = isDropdownExpanded,
        onDismissRequest = { isDropdownExpanded = false }
    ) {
        DropdownMenuItem(
            text = {
                Column {
                    Text(
                        "Top 10 Biggest economies of the world in 2024",
                        fontFamily = Fonts.semiBoldFontFamily
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = Color(0x80D9D9D9), thickness = 1.dp)
                }
            },
            onClick = {
                // Handle click action for this item
            }
        )
        countries.forEach { country ->
            DropdownMenuItem(
                text = { Text(country, fontFamily = Fonts.regularFontFamily) },
                onClick = {
                    text = country
                    isDropdownExpanded = false
                    onCountrySelected(country)
                }
            )
        }
    }
}
