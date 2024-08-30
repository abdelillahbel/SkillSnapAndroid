/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.utils

import android.content.Context

class PreferenceHelper(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("data_preference", Context.MODE_PRIVATE)

    fun isFirstRun(): Boolean {
        return sharedPreferences.getBoolean("is_first_run", true)
    }

    fun setFirstRun(isFirstRun: Boolean) {
        sharedPreferences.edit().putBoolean("is_first_run", isFirstRun).apply()
    }

}
