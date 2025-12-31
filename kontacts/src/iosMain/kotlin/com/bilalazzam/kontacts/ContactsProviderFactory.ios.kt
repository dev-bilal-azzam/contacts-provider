package com.bilalazzam.kontacts

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ContactsProviderFactory {
    actual fun createContactsProvider(): ContactsProvider = IosContactsProvider()
}