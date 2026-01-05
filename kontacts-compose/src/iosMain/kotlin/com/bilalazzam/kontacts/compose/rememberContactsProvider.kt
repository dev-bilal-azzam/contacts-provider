package com.bilalazzam.kontacts.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.bilalazzam.kontacts.api.ContactsProvider
import com.bilalazzam.kontacts.api.IosContactsProvider

@Composable
actual fun rememberContactsProvider(): ContactsProvider {
    return remember { IosContactsProvider() }
}