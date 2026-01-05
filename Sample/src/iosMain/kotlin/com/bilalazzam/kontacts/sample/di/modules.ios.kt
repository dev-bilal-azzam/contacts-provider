@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.bilalazzam.kontacts.sample.di

import com.bilalazzam.kontacts.core.ContactsProvider
import com.bilalazzam.kontacts.core.ContactsProviderFactory

actual class Dependencies {
    actual val contactsProvider: ContactsProvider = ContactsProviderFactory().createContactsProvider()
}