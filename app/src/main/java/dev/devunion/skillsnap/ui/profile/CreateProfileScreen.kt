/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.ui.profile


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.devunion.skillsnap.R
import dev.devunion.skillsnap.models.Contact
import dev.devunion.skillsnap.models.UserInfo
import dev.devunion.skillsnap.viewmodels.db.FirestoreViewModelInterface


@Composable
fun CreateProfileScreen(
    navController: NavController,
    viewModel: FirestoreViewModelInterface, // Injected ViewModel
    onProfileCreated: () -> Unit // Callback when profile is successfully created
) {
    val user = Firebase.auth.currentUser
    val currentUserId = user!!.uid

    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var avatar by remember { mutableStateOf("") }
    var resume by remember { mutableStateOf("") }
    var isUsernameTaken by remember { mutableStateOf(false) }
    var isCheckingUsername by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = "Create",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = {
                username = it
                isUsernameTaken = false
            },
            label = { Text("Username") },
            isError = isUsernameTaken
        )
        if (isUsernameTaken) {
            Text(
                text = "Username is already taken.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = about,
            onValueChange = { about = it },
            label = { Text("About") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = avatar,
            onValueChange = { avatar = it },
            label = { Text("Avatar URL") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = resume,
            onValueChange = { resume = it },
            label = { Text("Resume URL") }
        )

        Button(
            onClick = {
                if (username.isNotBlank() && name.isNotBlank() && bio.isNotBlank()) {
                    isCheckingUsername = true
                    viewModel.isUsernameAvailable(username, { isAvailable ->
                        isCheckingUsername = false
                        if (isAvailable) {
                            // Proceed with profile creation
                            val userInfo = UserInfo(
                                username = username,
                                id = currentUserId,
                                name = name,
                                status = "active",
                                role = "user",
                                bio = bio,
                                avatar = avatar,
                                about = about,
                                education = emptyMap(),
                                experience = emptyMap(),
                                projects = emptyMap(),
                                contact = Contact(email = user.email.toString(), phone = ""),
                                resume = resume,
                                createdAt = Timestamp.now(),
                            )

                            viewModel.saveUserInfo(
                                userInfo = userInfo,
                                onSuccess = {
                                    viewModel.updateHasProfileFlag(
                                        userId = currentUserId,
                                        hasProfile = true,
                                        onSuccess = {
                                            Toast.makeText(
                                                context,
                                                "Profile of $username created successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            onProfileCreated() // Navigate back or to another screen
                                        },
                                        onFailure = { exception ->
                                            Toast.makeText(
                                                context,
                                                "Error updating hasProfile flag: ${exception.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    )
                                },
                                onFailure = { exception ->
                                    Toast.makeText(
                                        context,
                                        "Error saving profile: ${exception.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        } else {
                            isUsernameTaken = true
                        }
                    }, {
                        isCheckingUsername = false
                        Toast.makeText(
                            context,
                            "Error checking username availability.",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                } else {
                    Toast.makeText(
                        context,
                        "Please fill in all required fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isCheckingUsername // Disable button while checking username
        ) {
            Text("Create Profile", fontSize = 16.sp)
        }
    }
}
