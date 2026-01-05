@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.bilalazzam.kontacts.sample.di

import com.bilalazzam.kontacts.api.ContactsProvider
import com.bilalazzam.kontacts.internal.factory.ContactsProviderFactory

actual class Dependencies {
    actual val contactsProvider: ContactsProvider = ContactsProviderFactory().createContactsProvider()
}