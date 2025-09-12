package com.bilalazzam.contacts_provider

import android.content.Context


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ContactsProviderFactory(private val context: Context) {
    actual fun createContactsProvider(): ContactsProvider = AndroidContactsProvider(context)
}