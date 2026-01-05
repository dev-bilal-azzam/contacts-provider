package com.bilalazzam.kontacts.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.bilalazzam.kontacts.core.ContactsProvider
import com.bilalazzam.kontacts.core.IosContactsProvider

@Composable
actual fun rememberContactsProvider(): ContactsProvider {
    return remember { IosContactsProvider() }
}