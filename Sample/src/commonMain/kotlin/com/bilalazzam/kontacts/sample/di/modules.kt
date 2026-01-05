package com.bilalazzam.kontacts.sample.di

import com.bilalazzam.kontacts.core.ContactsProvider

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class Dependencies{
    val contactsProvider: ContactsProvider
}

lateinit var dependencies: Dependencies