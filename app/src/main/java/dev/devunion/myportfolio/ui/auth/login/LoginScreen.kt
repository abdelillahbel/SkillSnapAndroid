/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

package dev.devunion.myportfolio.ui.auth.login


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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.devunion.myportfolio.R
import dev.devunion.myportfolio.navigation.ScreenRoutes
import dev.devunion.myportfolio.viewmodels.auth.AuthViewModelInterface


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
                    Text("Accept")
                }
            },
            modifier = Modifier.animateContentSize()  // Add animation to dialog appearance
        )
    }



    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.blurry_gradient_bg),
            contentDescription = "Login",
            modifier = Modifier
                .fillMaxSize()
                .blur(4.dp),
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(28.dp)
                .alpha(0.7f)
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                )
                .background(MaterialTheme.colorScheme.background)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                LoginHeader()
                Spacer(modifier = Modifier.height(20.dp))
                LoginFields(
                    email = authViewModel.email,
                    password = authViewModel.password,
                    onEmailChange = { authViewModel.email = it },
                    onPasswordChange = { authViewModel.password = it },
                    onForgotPasswordClick = {
                        navController.navigate(ScreenRoutes.ResetPasswordScreen.route)
//                        navController.toForgotPassword()
                    }
                )
                SignInFooter(
                    onSignInClick = {
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
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Welcome Back", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )
        Text(
            text = "Sign in to continue", fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )
    }
}


@Composable
fun LoginFields(
    email: String, password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Column {
        TextField(
            value = email,
            label = "Email",
            placeholder = "",
            onValueChange = onEmailChange,
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = password,
            label = "Password",
            placeholder = "",
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Password")
            }
        )

        TextButton(onClick = onForgotPasswordClick, modifier = Modifier.align(Alignment.End)) {
            Text(
                text = "Forgot Password?",
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
        }
    }
}

@Composable
fun SignInFooter(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = onSignInClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Sign In",
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
        }
        TextButton(onClick = onSignUpClick) {
            Text(
                text = "Don't have an account, click here",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
        }
    }
}

@Composable
fun TextField(
    value: String,
    label: String,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
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
