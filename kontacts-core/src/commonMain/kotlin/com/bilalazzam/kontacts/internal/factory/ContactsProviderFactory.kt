package com.bilalazzam.kontacts.internal.factory

import com.bilalazzam.kontacts.api.ContactsProvider

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ContactsProviderFactory {
    fun createContactsProvider(): ContactsProvider
}