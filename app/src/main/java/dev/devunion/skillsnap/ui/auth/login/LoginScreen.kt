/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.ui.auth.login


import android.util.Log
import android.util.Patterns
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.devunion.skillsnap.R
import dev.devunion.skillsnap.components.RegisterButton
import dev.devunion.skillsnap.components.RegisterIconButton
import dev.devunion.skillsnap.components.RegisterTextField
import dev.devunion.skillsnap.navigation.ScreenRoutes
import dev.devunion.skillsnap.ui.theme.SkillSnapTheme
import dev.devunion.skillsnap.viewmodels.auth.AuthViewModelInterface


@Composable
fun LoginScreen(authViewModel: AuthViewModelInterface, navController: NavController) {
    SetBarColor(color = MaterialTheme.colorScheme.background)

    val scrollState = rememberScrollState()
    // val context = LocalContext.current

    // Check if the user is already logged in
//    LaunchedEffect(Unit) {
//        authViewModel.isUserLoggedIn(onSuccess = { isLogged ->
//            if (isLogged) {
//                navController.navigate(ScreenRoutes.MainScreen.route) {
//                    popUpTo(ScreenRoutes.LoginScreen.route) { inclusive = true }
//                }
//            }
//        }, onFailure = { exception ->
//            Log.w("AuthScreen", "Error: ${exception.message}")
//            Toast.makeText(context, "Error: ${exception.message.toString()}", Toast.LENGTH_SHORT)
//                .show()
//        })
//    }

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
                LoginHeader()
                Spacer(modifier = Modifier.height(40.dp))
                SignInSection(
                    email = authViewModel.email,
                    password = authViewModel.password,
                    onEmailChange = { authViewModel.email = it },
                    onPasswordChange = { authViewModel.password = it },
                    onForgotPasswordClick = {
                        navController.navigate(ScreenRoutes.ResetPasswordScreen.route)
                    },
                    onSignInClick = {

                        when {
                            authViewModel.email.isEmpty() -> {
                                val emailError = "Please enter your email!"
                                dialogMessage = emailError
                                showDialog = true
                            }

                            !Patterns.EMAIL_ADDRESS.matcher(authViewModel.email).matches() -> {
                                val emailError = "Please enter a valid email address!"
                                dialogMessage = emailError
                                showDialog = true
                            }

                            authViewModel.password.isEmpty() -> {
                                val passwordError = "Please enter password!"
                                dialogMessage = passwordError
                                showDialog = true
                            }

                            else -> {
                                authViewModel.login(
                                    onSuccess = {
                                        navController.popBackStack()
                                        navController.navigate(ScreenRoutes.MainNav.route)
                                    },
                                    onFailure = { exception ->
                                        dialogMessage = exception.message.toString()
                                        showDialog = true
                                    }
                                )
                            }
                        }
                    },
                    onSignUpClick = {
                        navController.navigate(ScreenRoutes.SignUpScreen.route) {
                            popUpTo(ScreenRoutes.LoginScreen.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun LoginHeader() {
    Column(horizontalAlignment = CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.icon_foreground),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Sign in to continue", fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )
    }
}

@PreviewLightDark
@Composable
fun SignInSectionPrv() {
    SkillSnapTheme {
        //  SignInSection(email = "", password = "", onEmailChange = {}, onPasswordChange = {})
    }
}

@Composable
fun SignInSection(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }
    val iconPassword = rememberSaveable(showPassword) {
        if (showPassword) R.drawable.visibility_icon else R.drawable.visibility_off_icon
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = CenterHorizontally,
    ) {
        RegisterTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = stringResource(id = R.string.email_hint),
            isError = false,
            supportText = "",
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(id = R.string.email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        RegisterTextField(
            modifier = Modifier.fillMaxWidth(),
            hint = stringResource(id = R.string.password_hint),
            isError = false,
            supportText = "",
            value = password,
            onValueChange = onPasswordChange,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = painterResource(id = iconPassword),
            onTrailingIconClick = {
                showPassword = !showPassword
            },
            label = stringResource(id = R.string.password)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ClickableText(
            modifier = Modifier.align(Alignment.End),
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 17.sp
                    )
                ) {
                    append(stringResource(id = R.string.forget_password))
                }
            },
            onClick = {
                onForgotPasswordClick()
            }
        )

        Spacer(modifier = Modifier.height(30.dp))
        RegisterButton(
            text = stringResource(id = R.string.sign_in),
            onClick = { onSignInClick() },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(18.dp))
        TextButton(onClick = onSignUpClick) {
            Text(
                text = "Don't have an account?, Sign Up",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = stringResource(id = R.string.other_sign_in_options))
        Spacer(modifier = Modifier.height(30.dp))

        RegisterIconButton(icon = painterResource(id = R.drawable.google_icon)) {
            // TODO
        }
    }
}

@Composable
private fun SetBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = color
        )
    }
}
