package com.bilalazzam.kontacts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberContactsProvider(): ContactsProvider {
    val context = LocalContext.current.applicationContext
    return remember { AndroidContactsProvider(context) }
}