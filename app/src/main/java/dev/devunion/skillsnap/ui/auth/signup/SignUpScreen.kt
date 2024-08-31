/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.ui.auth.signup

import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.devunion.skillsnap.R
import dev.devunion.skillsnap.navigation.ScreenRoutes
import dev.devunion.skillsnap.ui.theme.SkillSnapTheme
import dev.devunion.skillsnap.viewmodels.auth.AuthViewModelInterface


@Composable
fun SignUpScreen(authViewModel: AuthViewModelInterface, navController: NavController) {

    val user = Firebase.auth.currentUser
    val scrollState = rememberScrollState()
    val context = LocalContext.current

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


    SkillSnapTheme {
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


                    SignUpHeader()
                    Spacer(modifier = Modifier.height(20.dp))
                    SignUpFields(
                        name = authViewModel.name,
                        email = authViewModel.email,
                        password = authViewModel.password,
                        confirmPassword = authViewModel.confirmPassword,
                        onNameChange = { authViewModel.name = it },
                        onEmailChange = { authViewModel.email = it },
                        onPasswordChange = { authViewModel.password = it },
                        onConfirmPasswordChange = { authViewModel.confirmPassword = it },
                        onForgotPasswordClick = {
//                        navController.toForgotPassword()
                            Toast.makeText(context, "Available soon", Toast.LENGTH_SHORT).show()
                        }
                    )
                    SignUpFooter(
                        onSignInClick = {
                            navController.popBackStack()
                            navController.navigate(ScreenRoutes.LoginScreen.route)
                        },
                        onSignUpClick = {


                            // Validate input fields
                            when {
                                authViewModel.name.isEmpty() -> {
                                    val nameError = "Name cannot be empty"
                                    dialogMessage = nameError
                                    showDialog = true
                                }

                                authViewModel.email.isEmpty() -> {
                                    val emailError = "Email cannot be empty"
                                    dialogMessage = emailError
                                    showDialog = true
                                }

                                !Patterns.EMAIL_ADDRESS.matcher(authViewModel.email).matches() -> {
                                    val emailError = "Please enter a valid email address"
                                    dialogMessage = emailError
                                    showDialog = true
                                }

                                authViewModel.password.isEmpty() -> {
                                    val passwordError = "Password cannot be empty"
                                    dialogMessage = passwordError
                                    showDialog = true
                                }

                                authViewModel.password.length < 6 -> {
                                    val passwordError = "Password must be at least 6 characters"
                                    dialogMessage = passwordError
                                    showDialog = true
                                }

                                authViewModel.confirmPassword.isEmpty() -> {
                                    val confirmPasswordError = "Please confirm your password"
                                    dialogMessage = confirmPasswordError
                                    showDialog = true
                                }

                                authViewModel.confirmPassword != authViewModel.password -> {
                                    val confirmPasswordError = "Passwords do not match"
                                    dialogMessage = confirmPasswordError
                                    showDialog = true
                                }

                                else -> {
                                    // Proceed with Firebase Auth
                                    authViewModel.register(
                                        onSuccess = {
                                            authViewModel.saveUserData(
                                                onSuccess = {
                                                    navController.navigate(ScreenRoutes.MainNav.route) {
                                                        popUpTo(ScreenRoutes.SignUpScreen.route) {
                                                            inclusive = true
                                                        }
                                                    }
                                                },
                                                onFailure = { exception ->
                                                    dialogMessage = exception.message.toString()
                                                    showDialog = true
                                                }
                                            )
                                        },
                                        onFailure = { exception ->
                                            dialogMessage = exception.message.toString()
                                            showDialog = true
                                        }
                                    )
                                }
                            }
                        }
                    )
                }
            }

        }
    }


}

@Composable
fun SignUpHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Sign Up", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily(Font(R.font.poppins_regular))

        )
        Text(
            text = "Create account for free", fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )
    }
}

@Composable
fun SignUpFields(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Column {
        TextField(
            value = name,
            label = "Name",
            placeholder = "",
            onValueChange = onNameChange,
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "Name")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(10.dp))
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
                imeAction = ImeAction.Next
            ),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Password")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = confirmPassword,
            label = "Retype Password",
            placeholder = "",
            onValueChange = onConfirmPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Password Confirmation")
            }
        )

        TextButton(onClick = onForgotPasswordClick, modifier = Modifier.align(Alignment.End)) {
            Text(text = "Forgot Password?", fontFamily = FontFamily(Font(R.font.poppins_regular)))
        }
    }
}

@Composable
fun SignUpFooter(
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = onSignUpClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Sign Up",
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
        }
        TextButton(onClick = onSignInClick) {
            Text(
                text = "Already have account?, click here",
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
