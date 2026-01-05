package com.bilalazzam.kontacts.internal.factory

import android.content.Context
import com.bilalazzam.kontacts.api.AndroidContactsProvider
import com.bilalazzam.kontacts.api.ContactsProvider


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ContactsProviderFactory(private val context: Context) {
    actual fun createContactsProvider(): ContactsProvider = AndroidContactsProvider(context)
}