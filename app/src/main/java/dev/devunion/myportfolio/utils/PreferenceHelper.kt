/*
 * Copyright (c) 2024. DevUnion Foundation.
 * All rights reserved.
 */

package dev.devunion.myportfolio.utils

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
