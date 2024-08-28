/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.myportfolio.viewmodels.auth

import dev.devunion.myportfolio.models.User


interface AuthViewModelInterface {

    var name: String
    var email: String
    var password: String
    var confirmPassword: String

    fun saveUserData(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    )

    fun register(onSuccess: (result: User) -> Unit, onFailure: (exception: Exception) -> Unit)
    fun login(onSuccess: (result: User) -> Unit, onFailure: (exception: Exception) -> Unit)
    fun isUserLoggedIn(
        onSuccess: (isLoggedIn: Boolean) -> Unit,
        onFailure: (exception: Exception) -> Unit
    )

    fun recoverPassword(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit)
    fun getUser(onSuccess: (result: User) -> Unit, onFailure: (exception: Exception) -> Unit)
    fun getUserData(
        onSuccess: (user: User) -> Unit,
        onFailure: (exception: Exception) -> Unit
    )
    fun logout(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit)
}
