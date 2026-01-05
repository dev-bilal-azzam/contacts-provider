package com.bilalazzam.kontacts.sample.di

import android.content.Context
import com.bilalazzam.kontacts.core.ContactsProvider
import com.bilalazzam.kontacts.core.ContactsProviderFactory

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Dependencies(private val context: Context) {
    actual val contactsProvider: ContactsProvider = ContactsProviderFactory(context).createContactsProvider()
}