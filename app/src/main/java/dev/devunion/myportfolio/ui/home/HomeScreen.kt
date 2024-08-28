/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.myportfolio.ui.home

import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.devunion.myportfolio.R

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}

@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current

    val devunionPageUrl = "https://www.devunion.dev/"

    val poppinsFamily = FontFamily(
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_bold, FontWeight.Bold)
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = "Home",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium
        )
        // Animated Text
        AnimatedText(
            text = "People have a portfolio to showcase their skills and summary to the public, and you deserve one. Here is your portfolio.",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            modifier = Modifier.padding(bottom = 24.dp)
        )


        Card(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .padding(16.dp, 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    modifier = Modifier
                        .animateContentSize()
                        .padding(start = 2.dp, end = 4.dp)
                        .align(Alignment.CenterVertically),
                    text = "Explore DevUnion website",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Start,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium
                )
                Button(onClick = {
                    CustomTabsIntent
                        .Builder()
                        .build()
                        .launchUrl(context, Uri.parse(devunionPageUrl))
                }) {
                    Text("Visit")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth()
        ) {


            Row(
                Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .padding(16.dp, 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    modifier = Modifier
                        .animateContentSize()
                        .padding(start = 2.dp, end = 4.dp)
                        .align(Alignment.CenterVertically),
                    text = "Share the app with your friends",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Start,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium
                )
                Button(onClick = {
                    CustomTabsIntent
                        .Builder()
                        .build()
                        .launchUrl(context, Uri.parse(devunionPageUrl))
                }) {
                    Text("Share")
                }
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
        // Awesome Element: Animated Circular Progress Bar with a Glow Effect
        AwesomeCircularProgressBar(
            progress = 0.75f, // Example progress
            size = 200.dp,
            strokeWidth = 18.dp,
            colors = listOf(Color(0xFF1E88E5), Color(0xFF1E88E5)), // Gradient colors
            glowColor = Color(0xFF1E88E5),//0xFF42A5F5
            animationDuration = 5000
        )
    }
}

@Composable
fun AnimatedText(
    text: String,
    fontSize: TextUnit,
    color: Color,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier
) {
//    val animatedAlpha = remember { Animatable(0f) }
//
//    LaunchedEffect(Unit) {
//        animatedAlpha.animateTo(
//            targetValue = 1f,
//            animationSpec = tween(durationMillis = 5000)
//        )
//    }

    Text(
        text = text,
        fontSize = fontSize,
//        color = color.copy(alpha = animatedAlpha.value),
        fontFamily = fontFamily,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun AwesomeCircularProgressBar(
    progress: Float,
    size: Dp,
    strokeWidth: Dp,
    colors: List<Color>,
    glowColor: Color,
    animationDuration: Int
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        animatedProgress.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = animationDuration)
        )
    }

    Canvas(modifier = Modifier.size(size)) {
        val sweepAngle = animatedProgress.value * 360
        drawArc(
            brush = Brush.sweepGradient(colors),
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
        )

        drawArc(
            color = glowColor,
            startAngle = sweepAngle - 90f,
            sweepAngle = 10f,
            useCenter = false,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
            alpha = 0.5f
        )
    }
}
