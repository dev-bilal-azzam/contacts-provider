package com.bilalazzam.kontacts

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ContactsProviderFactory {
    fun createContactsProvider(): ContactsProvider
}