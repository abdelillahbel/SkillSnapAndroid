/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

package dev.devunion.myportfolio.viewmodels.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dev.devunion.myportfolio.models.User

class FirebaseAuthViewModel : AuthViewModelInterface, ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = FirebaseFirestore.getInstance()

    private var _email: String by mutableStateOf("")
    override var email: String
        get() = _email
        set(value) {
            _email = value
        }

    private var _password: String by mutableStateOf("")
    override var password: String
        get() = _password
        set(value) {
            _password = value
        }

    private var _confirmPassword: String by mutableStateOf("")
    override var confirmPassword: String
        get() = _confirmPassword
        set(value) {
            _confirmPassword = value
        }

    override fun register(
        onSuccess: (result: User) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(User(id = auth.currentUser?.uid ?: "", email = email))
                } else {
                    onFailure(task.exception ?: Exception("Unknown exception."))
                }
            }
    }

    override fun login(
        onSuccess: (result: User) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(User(id = auth.currentUser?.uid ?: "", email = email))
                } else {
                    onFailure(task.exception ?: Exception("Unknown exception."))
                }
            }
    }

    override fun saveUserData(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        val userId = auth.currentUser!!.uid
        val userData = User(id = userId, email = email)

        firestore.collection("users")
            .document(userId)
            .set(userData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    override fun isUserLoggedIn(
        onSuccess: (isLoggedIn: Boolean) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        val currentUser = auth.currentUser
        onSuccess(currentUser != null)
    }

    override fun recoverPassword(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception ?: Exception("Unknown exception."))
                }
            }
    }

    override fun getUser(
        onSuccess: (result: User) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            onSuccess(User(id = currentUser.uid, email = currentUser.email ?: ""))
        } else {
            onFailure(Exception("User is not logged in."))
        }
    }

    override fun logout(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit) {
        try {
            auth.signOut()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}
