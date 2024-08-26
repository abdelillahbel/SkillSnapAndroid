/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

package dev.devunion.myportfolio.viewmodels.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.devunion.myportfolio.models.User
import kotlinx.coroutines.*

class DummyAuthViewModel : AuthViewModelInterface, ViewModel() {

    private val user = Firebase.auth.currentUser

    private var _name: String by mutableStateOf("")
    override var name: String
        get() = _name
        set(value) {
            _name = value
        }

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
        viewModelScope.launch {
            val result = async {
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                    User(id = user!!.uid, email = email, name = name)
                } else {
                    throw Exception("Email or password is empty.")
                }
            }
            try {
                onSuccess(result.await())
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    override fun saveUserData(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        // Simulate a delay for testing
        try {
            // Assuming saving is always successful in the dummy implementation
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override fun login(
        onSuccess: (result: User) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        register(onSuccess, onFailure)
    }

    override fun isUserLoggedIn(
        onSuccess: (isLoggedIn: Boolean) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        onSuccess(false)
    }

    override fun recoverPassword(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit) {
        viewModelScope.launch {
            try {
                onSuccess()
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    override fun getUser(
        onSuccess: (result: User) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        viewModelScope.launch {
            val result = async {
                User(id = user!!.uid, email = email, name = name)
            }
            try {
                onSuccess(result.await())
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    override fun getUserData(
        onSuccess: (user: User) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        // Simulate a successful retrieval of user data
        val dummyUser = User(
            id = "dummy-id",
            email = "dummy@example.com",
            name = "Dummy User"
        )
        onSuccess(dummyUser)
    }
    override fun logout(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit) {
        onSuccess()
    }
}
