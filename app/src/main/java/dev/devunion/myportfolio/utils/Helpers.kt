package dev.devunion.myportfolio.utils

import android.os.Build


public fun shouldShowSplashScreen(currentApiVersion: Int): Boolean {
    return currentApiVersion < Build.VERSION_CODES.S
}
