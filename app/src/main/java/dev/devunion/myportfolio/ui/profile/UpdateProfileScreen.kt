package dev.devunion.myportfolio.ui.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.devunion.myportfolio.models.Education
import dev.devunion.myportfolio.models.Experience
import dev.devunion.myportfolio.models.Project
import dev.devunion.myportfolio.models.UserInfo
import dev.devunion.myportfolio.viewmodels.db.FirebaseFirestoreViewModel
import dev.devunion.myportfolio.viewmodels.db.FirestoreViewModelInterface
import java.util.UUID


@Composable
fun UpdateProfileScreen(
    navController: NavController,
    viewModel: FirestoreViewModelInterface,
) {
    var userInfo by remember { mutableStateOf<UserInfo?>(null) }
    val context = LocalContext.current
    val username = "anis_z3"

    // Fetch user data on screen load
    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo(
            username = username,
            onSuccess = { fetchedUserInfo ->
                userInfo = fetchedUserInfo
            },
            onFailure = { exception ->
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
                Log.i("TAG", "UpdateProfileScreen: ${exception.message}")
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        userInfo?.let { user ->

            // Updating user details like name, bio, etc.
            TextField(
                value = user.name,
                onValueChange = { newName ->
                    userInfo = user.copy(name = newName)
                },
                label = { Text("Name") }
            )

            TextField(
                value = user.bio,
                onValueChange = { newBio ->
                    userInfo = user.copy(bio = newBio)
                },
                label = { Text("Bio") }
            )

            // Project Section
            ProjectSection(
                projects = user.projects,
                onAddProject = { newProject ->
                    val updatedProjects = user.projects.toMutableMap()
                    val projectId = UUID.randomUUID().toString()
                    updatedProjects[projectId] = newProject
                    userInfo = user.copy(projects = updatedProjects)
                },
                onUpdateProject = { id, updatedProject ->
                    val updatedProjects = user.projects.toMutableMap()
                    updatedProjects[id] = updatedProject
                    userInfo = user.copy(projects = updatedProjects)
                }
            )

            // Education Section
            EducationSection(
                education = user.education,
                onAddEducation = { newEducation ->
                    val updatedEducation = user.education.toMutableMap()
                    val educationId = UUID.randomUUID().toString()
                    updatedEducation[educationId] = newEducation
                    userInfo = user.copy(education = updatedEducation)
                },
                onUpdateEducation = { id, updatedEducation ->
                    val updatedEducationMap = user.education.toMutableMap()
                    updatedEducationMap[id] = updatedEducation
                    userInfo = user.copy(education = updatedEducationMap)
                }
            )

            // Experience Section
            ExperienceSection(
                experience = user.experience,
                onAddExperience = { newExperience ->
                    val updatedExperience = user.experience.toMutableMap()
                    val experienceId = UUID.randomUUID().toString()
                    updatedExperience[experienceId] = newExperience
                    userInfo = user.copy(experience = updatedExperience)
                },
                onUpdateExperience = { id, updatedExperience ->
                    val updatedExperienceMap = user.experience.toMutableMap()
                    updatedExperienceMap[id] = updatedExperience
                    userInfo = user.copy(experience = updatedExperienceMap)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Save Changes Button
            Button(onClick = {
                viewModel.saveUserInfo(userInfo!!, onSuccess = {
                    Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT)
                        .show()
                    navController.popBackStack() // Navigate back after saving
                }, onFailure = { exception ->
                    Toast.makeText(
                        context,
                        "Failed to update profile: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }) {
                Text("Save Changes")
            }
        } ?: run {
            // Handle the case when userInfo is null, e.g., show a loading indicator or message
            Text("Loading...")
        }
    }
}

@Composable
fun ExperienceSection(
    experience: Map<String, Experience>,
    onAddExperience: (Experience) -> Unit,
    onUpdateExperience: (String, Experience) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Experience", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        experience.forEach { (id, experience) ->
            ExperienceItem(
                experience = experience,
                onUpdateExperience = { updatedExperience ->
                    onUpdateExperience(id, updatedExperience)
                }, onAddExperience = { addExperience ->
                    // onAddExperience(id, addExperience) TODO

                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onAddExperience(
                Experience(
                    title = "New Experience",
                    company = "",
                    period = "",
                    description = ""
                )
            )
        }) {
            Text("Add New Experience")
        }
    }
}

@Composable
fun ExperienceItem(
    experience: Experience,
    onUpdateExperience: (Experience) -> Unit,
    onAddExperience: (Experience) -> Unit
) {
    var title by remember { mutableStateOf(experience.title) }
    var company by remember { mutableStateOf(experience.company) }
    var period by remember { mutableStateOf(experience.period) }
    var description by remember { mutableStateOf(experience.description) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                onUpdateExperience(experience.copy(title = it))
            },
            label = { Text("Title") }
        )

        OutlinedTextField(
            value = company,
            onValueChange = {
                company = it
                onUpdateExperience(experience.copy(company = it))
            },
            label = { Text("Company") }
        )

        OutlinedTextField(
            value = period,
            onValueChange = {
                period = it
                onUpdateExperience(experience.copy(period = it))
            },
            label = { Text("Period") }
        )

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                onUpdateExperience(experience.copy(description = it))
            },
            label = { Text("Description") },
            modifier = Modifier.height(100.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun EducationSection(
    education: Map<String, Education>,
    onAddEducation: (Education) -> Unit,
    onUpdateEducation: (String, Education) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Education", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        education.forEach { (id, education) ->
            EducationItem(
                education = education,
                onUpdateEducation = { updatedEducation ->
                    onUpdateEducation(id, updatedEducation)
                },
                onAddEducation = { addEducation ->
                    // onAddEducation(id, addEducation) TODO
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onAddEducation(
                Education(
                    degree = "New Degree",
                    institution = "",
                    year = ""
                )
            )
        }) {
            Text("Add New Education")
        }
    }
}

@Composable
fun EducationItem(
    education: Education,
    onUpdateEducation: (Education) -> Unit,
    onAddEducation: (Education) -> Unit
) {
    var degree by remember { mutableStateOf(education.degree) }
    var institution by remember { mutableStateOf(education.institution) }
    var year by remember { mutableStateOf(education.year) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = degree,
            onValueChange = {
                degree = it
                onUpdateEducation(education.copy(degree = it))
            },
            label = { Text("Degree") }
        )

        OutlinedTextField(
            value = institution,
            onValueChange = {
                institution = it
                onUpdateEducation(education.copy(institution = it))
            },
            label = { Text("Institution") }
        )

        OutlinedTextField(
            value = year,
            onValueChange = {
                year = it
                onUpdateEducation(education.copy(year = it))
            },
            label = { Text("Year") }
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ProjectSection(
    projects: Map<String, Project>,
    onAddProject: (Project) -> Unit,
    onUpdateProject: (String, Project) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Projects", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        projects.forEach { (id, project) ->
            ProjectItem(
                project = project,
                onUpdateProject = { updatedProject ->
                    onUpdateProject(id, updatedProject)
                },
                onAddProject = {
                    // TODO
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onAddProject(
                Project(
                    title = "New Project",
                    description = "",
                    image = null
                )
            )
        }) {
            Text("Add New Project")
        }
    }
}

@Composable
fun ProjectItem(
    project: Project,
    onUpdateProject: (Project) -> Unit,
    onAddProject: (Project) -> Unit
) {
    var title by remember { mutableStateOf(project.title) }
    var description by remember { mutableStateOf(project.description) }
    var imageLink by remember { mutableStateOf(project.image ?: "") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                onUpdateProject(project.copy(title = it))
            },
            label = { Text("Title") }
        )

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                onUpdateProject(project.copy(description = it))
            },
            label = { Text("Description") },
            modifier = Modifier.height(100.dp)
        )

        OutlinedTextField(
            value = imageLink,
            onValueChange = {
                imageLink = it
                onUpdateProject(project.copy(image = it))
            },
            label = { Text("Image Link") }
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}
