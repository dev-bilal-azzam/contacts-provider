package com.bilalazzam.contacts_provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberContactsProvider(): ContactsProvider {
    return remember { IosContactsProvider() }
}