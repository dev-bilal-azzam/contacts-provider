package com.bilalazzam.kontacts

import androidx.compose.runtime.Composable

// exposed api for instantiating ContactsProvider
@Composable
expect fun rememberContactsProvider(): ContactsProvider