/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.ui.auth.reset_password

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.devunion.skillsnap.R
import dev.devunion.skillsnap.ui.theme.SkillSnapTheme
import dev.devunion.skillsnap.viewmodels.auth.AuthViewModelInterface
import dev.devunion.skillsnap.viewmodels.auth.DummyAuthViewModel

@Preview
@Composable
fun ResetPasswordScreenPreview() {
    val navController = rememberNavController()
    val authViewModel: DummyAuthViewModel = viewModel()
    ResetPasswordScreen(authViewModel = authViewModel, navController = navController)
}

@Composable
fun ResetPasswordScreen(authViewModel: AuthViewModelInterface, navController: NavController) {

    val context = LocalContext.current

    val poppinsFamily = FontFamily(
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_bold, FontWeight.Bold)
    )
    val scrollState = rememberScrollState()

    SkillSnapTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var showDialog by remember { mutableStateOf(false) }
            var dialogMessage by remember { mutableStateOf("") }

            // Show alert dialog if needed
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    text = { Text(dialogMessage) },
                    confirmButton = {
                        Button(
                            onClick = { showDialog = false }
                        ) {
                            Text("Okay")
                        }
                    },
                    modifier = Modifier.animateContentSize()  // Add animation to dialog appearance
                )
            }



            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                Text(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    text = "Enter your email bellow :",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = authViewModel.email,
                    onValueChange = { authViewModel.email = it },
                    label = { Text("Email") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        when {
                            authViewModel.email.isEmpty() -> {
                                val emailError = "Please enter your email!"
                                dialogMessage = emailError
                                showDialog = true
                            }

                            else -> {
                                authViewModel.recoverPassword(
                                    onSuccess = {
                                        dialogMessage = "Password reset link sent, check your email"
                                        showDialog = true
                                    },
                                    onFailure = { exception ->
                                        dialogMessage = exception.message.toString()
                                        showDialog = true
                                    }
                                )
                            }
                        }

                    },
                    modifier = Modifier
                        .animateContentSize(),
                    shape = RoundedCornerShape(20f)
                ) {
                    Text("Recover Password")
                }
            }

        }
    }
}
