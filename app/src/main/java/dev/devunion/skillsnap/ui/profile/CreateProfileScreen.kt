/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.ui.profile


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.devunion.skillsnap.R
import dev.devunion.skillsnap.models.Contact
import dev.devunion.skillsnap.models.UserInfo
import dev.devunion.skillsnap.utils.getBitmapFromUri
import dev.devunion.skillsnap.viewmodels.db.FirestoreViewModelInterface
import dev.devunion.skillsnap.viewmodels.storage.StorageViewModelInterface


@Composable
fun CreateProfileScreen(
    navController: NavController,
    viewModel: FirestoreViewModelInterface,
    storageViewModel: StorageViewModelInterface,
    onProfileCreated: () -> Unit
) {
    val mUser = Firebase.auth.currentUser
    val currentUserId = mUser!!.uid

    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var resume by remember { mutableStateOf("") }
    var isUsernameTaken by remember { mutableStateOf(false) }
    var isCheckingUsername by remember { mutableStateOf(false) }
    var isUsernameValid by remember { mutableStateOf(true) }

    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
        }
    )

    var showLoadingDialog by remember { mutableStateOf(false) }

    if (showLoadingDialog) {
        Dialog(onDismissRequest = { showLoadingDialog = false }) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    val usernameRegex = "^[a-z0-9_]+$".toRegex()

    fun validateUsername(username: String): Boolean {
        return usernameRegex.matches(username)
    }

    fun checkUsernameAvailability(username: String) {
        isCheckingUsername = true
        viewModel.isUsernameAvailable(username, { isAvailable ->
            isUsernameTaken = !isAvailable
            isCheckingUsername = false
        }, {
            isCheckingUsername = false
            Toast.makeText(
                context,
                "Error checking username availability.",
                Toast.LENGTH_SHORT
            ).show()
        })
    }

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

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = CircleShape
                    )
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    }
            )
        } ?: run {
            Image(
                painter = rememberAsyncImagePainter(""),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = CircleShape
                    )
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = {
                username = it
                isUsernameTaken = false
                isUsernameValid = validateUsername(it)
                if (isUsernameValid && username.isNotBlank()) {
                    checkUsernameAvailability(it)
                }
            },
            label = { Text("Username") },
            isError = !isUsernameValid || isUsernameTaken
        )
        if (!isUsernameValid) {
            Text(
                text = "Username can only contain lowercase letters, numbers, and underscores.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
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
            value = resume,
            onValueChange = { resume = it },
            label = { Text("Resume URL") }
        )

        Text(
            modifier = Modifier
                .padding(16.dp, 8.dp),
            text = "Other details can be added after profile creation success.",
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = {
                showLoadingDialog = true
                if (username.isNotBlank() && name.isNotBlank() && bio.isNotBlank()) {
                    isCheckingUsername = true
                    viewModel.isUsernameAvailable(username, { isAvailable ->
                        isCheckingUsername = false
                        if (isAvailable) {
                            imageUri?.let { uri ->
                                val bitmap = getBitmapFromUri(context, uri)
                                bitmap?.let {
                                    storageViewModel.uploadImage(it,
                                        onSuccess = { downloadUrl ->
                                            val userInfo = UserInfo(
                                                username = username,
                                                id = currentUserId,
                                                name = name,
                                                status = "active",
                                                role = "user",
                                                bio = bio,
                                                avatar = downloadUrl,
                                                about = about,
                                                education = emptyMap(),
                                                experience = emptyMap(),
                                                projects = emptyMap(),
                                                contact = Contact(
                                                    email = mUser.email.toString(),
                                                    phone = ""
                                                ),
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
                                                            onProfileCreated()
                                                            showLoadingDialog = false
                                                        },
                                                        onFailure = { exception ->
                                                            showLoadingDialog = false
                                                            Toast.makeText(
                                                                context,
                                                                "Error updating hasProfile flag: ${exception.message}",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    )
                                                },
                                                onFailure = { exception ->
                                                    showLoadingDialog = false
                                                    Toast.makeText(
                                                        context,
                                                        "Error saving profile: ${exception.message}",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            )
                                        },
                                        onFailure = { exception ->
                                            showLoadingDialog = false
                                            Toast.makeText(
                                                context,
                                                "Error: ${exception.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        })
                                }
                            } ?: run {
                                val userInfo = UserInfo(
                                    username = username,
                                    id = currentUserId,
                                    name = name,
                                    status = "active",
                                    role = "user",
                                    bio = bio,
                                    avatar = "",
                                    about = about,
                                    education = emptyMap(),
                                    experience = emptyMap(),
                                    projects = emptyMap(),
                                    contact = Contact(email = mUser.email.toString(), phone = ""),
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
                                                onProfileCreated()
                                                showLoadingDialog = false
                                            },
                                            onFailure = { exception ->
                                                showLoadingDialog = false
                                                Toast.makeText(
                                                    context,
                                                    "Error updating hasProfile flag: ${exception.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    },
                                    onFailure = { exception ->
                                        showLoadingDialog = false
                                        Toast.makeText(
                                            context,
                                            "Error saving profile: ${exception.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                        } else {
                            showLoadingDialog = false
                            isUsernameTaken = true
                        }
                    }, {
                        showLoadingDialog = false
                        isCheckingUsername = false
                        Toast.makeText(
                            context,
                            "Error checking username availability.",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                } else {
                    showLoadingDialog = false
                    Toast.makeText(
                        context,
                        "Please fill in all required fields.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isUsernameTaken && !isCheckingUsername && isUsernameValid && username.isNotBlank() && name.isNotBlank() && bio.isNotBlank()
        ) {
            Text(text = "Create Profile")
        }
    }
}
