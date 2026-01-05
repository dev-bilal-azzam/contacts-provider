package com.bilalazzam.kontacts.compose

import androidx.compose.runtime.Composable
import com.bilalazzam.kontacts.core.ContactsProvider

// exposed api for instantiating ContactsProvider for compose

@Composable
expect fun rememberContactsProvider(): ContactsProvider