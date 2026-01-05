package com.bilalazzam.kontacts.internal.factory

import com.bilalazzam.kontacts.api.ContactsProvider
import com.bilalazzam.kontacts.api.IosContactsProvider

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ContactsProviderFactory {
    actual fun createContactsProvider(): ContactsProvider = IosContactsProvider()
}