package dev.devunion.myportfolio.ui.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.devunion.myportfolio.models.Education
import dev.devunion.myportfolio.models.Experience
import dev.devunion.myportfolio.models.Project
import dev.devunion.myportfolio.models.UserInfo
import dev.devunion.myportfolio.navigation.ScreenRoutes
import dev.devunion.myportfolio.viewmodels.db.FirebaseFirestoreViewModel
import dev.devunion.myportfolio.viewmodels.db.FirestoreViewModelInterface
import java.util.UUID

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    // ProfileScreen(navController = navController)
}

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: FirestoreViewModelInterface
) {
    var showEditExperienceDialog by remember { mutableStateOf<Pair<String, Experience>?>(null) }
    val user = Firebase.auth.currentUser
    val currentUserId = user!!.uid

    var userInfo by remember { mutableStateOf<UserInfo?>(null) }
    var hasProfile by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Log.i("TAG", "ProfileScreen: ${currentUserId}")

    // Check if the user has a profile
    LaunchedEffect(Unit) {
        viewModel.checkUserHasProfile(
            userId = currentUserId,
            onSuccess = { profileExists ->
                hasProfile = profileExists
                if (profileExists) {
                    // Fetch the profile data
                    viewModel.fetchUserProfile(
                        userId = currentUserId,
                        onSuccess = { fetchedUserInfo ->
                            userInfo = fetchedUserInfo
                        },
                        onFailure = { exception ->
                            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            },
            onFailure = { exception ->
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        if (hasProfile) {
            userInfo?.let { user ->
                // Display user general info
                Text(text = "Name: ${user.name}", style = MaterialTheme.typography.headlineSmall)
                Text(text = "Status: ${user.status}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Role: ${user.role}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Bio: ${user.bio}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "About: ${user.about}", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(16.dp))

                // Projects section
                ExpandableSection(
                    title = "Projects",
                    content = {
                        user.projects.forEach { (id, project) ->
                            ProjectItem(
                                project = project,
                                onUpdate = { updatedProject ->
                                    val updatedProjects = user.projects.toMutableMap()
                                    updatedProjects[id] = updatedProject
                                    viewModel.saveUserInfo(
                                        user.copy(projects = updatedProjects),
                                        onSuccess = {
                                            // Handle success
                                        },
                                        onFailure = { exception ->
                                            // Handle failure
                                        })
                                }
                            )
                        }
                    }
                )

                // Education section
                ExpandableSection(
                    title = "Education",
                    content = {
                        user.education.forEach { (id, education) ->
                            EducationItem(
                                education = education,
                                onUpdate = { updatedEducation ->
                                    val updatedEducationMap = user.education.toMutableMap()
                                    updatedEducationMap[id] = updatedEducation
                                    viewModel.saveUserInfo(
                                        user.copy(education = updatedEducationMap),
                                        onSuccess = {
                                            // Handle success
                                        },
                                        onFailure = { exception ->
                                            // Handle failure
                                        })
                                }
                            )
                        }
                    }
                )

                // Experience section
                ExpandableSection(
                    title = "Experience",
                    content = {
                        user.experience.forEach { (id, experience) ->
                            ExperienceItem(
                                experience = experience,
                                onUpdate = { updatedExperience ->
                                    val updatedExperienceMap = user.experience.toMutableMap()
                                    updatedExperienceMap[id] = updatedExperience
                                    viewModel.saveUserInfo(
                                        user.copy(experience = updatedExperienceMap),
                                        onSuccess = {
                                            // Handle success
                                        },
                                        onFailure = { exception ->
                                            // Handle failure
                                        })
                                }
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(onClick = {
                    viewModel.saveUserInfo(user, onSuccess = {
                        // Handle success
                    }, onFailure = { exception ->
                        // Handle failure
                    })
                }) {
                    Text("Save Changes")
                }

                Button(onClick = {
                    navController.navigate(ScreenRoutes.UpdateProfileScreen.route)
                }) {
                    Text("Update")
                }

                // Show edit dialog if needed
                showEditExperienceDialog?.let { (id, experience) ->
                    EditExperienceDialog(
                        experience = experience,
                        onDismiss = { showEditExperienceDialog = null },
                        onSave = { updatedExperience ->
                            val updatedExperienceMap = user.experience.toMutableMap()
                            updatedExperienceMap[id] = updatedExperience
                            viewModel.saveUserInfo(
                                user.copy(experience = updatedExperienceMap),
                                onSuccess = {
                                    // Handle success
                                },
                                onFailure = { exception ->
                                    // Handle failure
                                })
                        }
                    )
                }
            }


        } else {
            Button(onClick = {
                navController.navigate(ScreenRoutes.CreateProfileScreen.route)
            }) {
                Text("Create Profile")
            }
        }
    }
}

@Composable
fun ExpandableSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (expanded) "Collapse" else "Expand"
            )
        }
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(start = 16.dp)) {
                content()
            }
        }
    }
}

@Composable
fun ProjectItem(
    project: Project,
    onUpdate: (Project) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = project.title, style = MaterialTheme.typography.headlineSmall)
        Text(text = project.description, style = MaterialTheme.typography.bodyMedium)
        if (project.image != null) {
            Image(
                painter = rememberAsyncImagePainter(project.image),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDialog = true }) {
            Text("Edit")
        }

        if (showDialog) {
            EditProjectDialog(project = project, onUpdate = {
                onUpdate(it)
                showDialog = false
            }, onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun EducationItem(
    education: Education,
    onUpdate: (Education) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "${education.degree} from ${education.institution}",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = "Year: ${education.year}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDialog = true }) {
            Text("Edit")
        }

        if (showDialog) {
            EditEducationDialog(education = education, onUpdate = {
                onUpdate(it)
                showDialog = false
            }, onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun EditProjectDialog(
    project: Project,
    onUpdate: (Project) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf(project.title) }
    var description by remember { mutableStateOf(project.description) }
    var image by remember { mutableStateOf(project.image) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Project") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                // Add Image picker if needed
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onUpdate(project.copy(title = title, description = description, image = image))
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun EditEducationDialog(
    education: Education,
    onUpdate: (Education) -> Unit,
    onDismiss: () -> Unit
) {
    var degree by remember { mutableStateOf(education.degree) }
    var institution by remember { mutableStateOf(education.institution) }
    var year by remember { mutableStateOf(education.year) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Education") },
        text = {
            Column {
                OutlinedTextField(
                    value = degree,
                    onValueChange = { degree = it },
                    label = { Text("Degree") }
                )
                OutlinedTextField(
                    value = institution,
                    onValueChange = { institution = it },
                    label = { Text("Institution") }
                )
                OutlinedTextField(
                    value = year,
                    onValueChange = { year = it },
                    label = { Text("Year") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onUpdate(
                        education.copy(
                            degree = degree,
                            institution = institution,
                            year = year
                        )
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ExperienceItem(
    experience: Experience,
    onUpdate: (Experience) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var updatedExperience by remember { mutableStateOf(experience) }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = experience.title, style = MaterialTheme.typography.headlineSmall)
        Text(text = experience.company, style = MaterialTheme.typography.displayMedium)
        Text(text = experience.period, style = MaterialTheme.typography.bodySmall)
        Text(text = experience.description, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { isEditing = true }) {
            Text("Edit")
        }

        if (isEditing) {
            EditExperienceDialog(
                experience = updatedExperience,
                onDismiss = { isEditing = false },
                onSave = { editedExperience ->
                    updatedExperience = editedExperience
                    onUpdate(updatedExperience)
                    isEditing = false
                }
            )
        }
    }
}

@Composable
fun EditExperienceDialog(
    experience: Experience,
    onDismiss: () -> Unit,
    onSave: (Experience) -> Unit
) {
    var title by remember { mutableStateOf(experience.title) }
    var company by remember { mutableStateOf(experience.company) }
    var period by remember { mutableStateOf(experience.period) }
    var description by remember { mutableStateOf(experience.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Experience") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                TextField(
                    value = company,
                    onValueChange = { company = it },
                    label = { Text("Company") }
                )
                TextField(
                    value = period,
                    onValueChange = { period = it },
                    label = { Text("Period") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        Experience(
                            title = title,
                            company = company,
                            period = period,
                            description = description
                        )
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
