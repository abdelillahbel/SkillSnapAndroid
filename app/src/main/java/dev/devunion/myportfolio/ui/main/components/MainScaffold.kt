/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.myportfolio.ui.main.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import dev.devunion.myportfolio.navigation.MainNavGraph
import dev.devunion.myportfolio.viewmodels.auth.AuthViewModelInterface


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun ScaffoldDemo() {
    MainScaffold {}
}


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun MainScaffold(logout: () -> Unit) {
    val navController = rememberNavController()
    LocalContext.current.applicationContext

    // app content
    // add scaffold here
    Scaffold(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.onBackground)
//                .padding(with(LocalDensity.current) {
//                    PaddingValues(
//                        top = WindowInsets.statusBars
//                            .getTop(this)
//                            .toDp(),
//                        bottom = WindowInsets.navigationBars
//                            .getBottom(this)
//                            .toDp()
//                    )
//                }),
        modifier = Modifier
            .padding(with(LocalDensity.current) {
                PaddingValues(
                    top = WindowInsets.statusBars
                        .getTop(this)
                        .toDp()
                )
            }),
//        topBar = {
//            Column(modifier = Modifier.padding(12.dp)) {
//                MainTopAppBar()
//
//            }
//
//      }
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues = paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {

                MainNavGraph(navController = navController, logout)
            }
        },
        bottomBar = {
            MainBottomBar(navController)
        },

        )
}
