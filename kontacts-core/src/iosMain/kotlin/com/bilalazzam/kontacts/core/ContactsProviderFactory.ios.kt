package com.bilalazzam.kontacts.core


@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class ContactsProviderFactory {
    actual fun createContactsProvider(): ContactsProvider  = IosContactsProvider()
}