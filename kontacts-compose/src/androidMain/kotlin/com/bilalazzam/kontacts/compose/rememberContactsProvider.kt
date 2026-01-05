package com.bilalazzam.kontacts.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bilalazzam.kontacts.core.AndroidContactsProvider
import com.bilalazzam.kontacts.core.ContactsProvider

@Composable
actual fun rememberContactsProvider(): ContactsProvider {
    val context = LocalContext.current.applicationContext
    return remember { AndroidContactsProvider(context) }
}