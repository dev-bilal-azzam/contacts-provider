package com.bilalazzam.contacts_provider

import androidx.compose.runtime.Composable

// exposed api for instantiating ContactsProvider
@Composable
expect fun rememberContactsProvider(): ContactsProvider