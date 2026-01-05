package com.bilalazzam.kontacts.internal.utils

import com.bilalazzam.kontacts.core.Contact

internal fun List<Contact>.mergeDuplicates(): List<Contact> {
    return this
        .groupBy { it.displayName }
        .map { (_, group) ->
            val allNumbers = group.flatMap { it.phoneNumbers }.distinct()
            val firstContact = group.first()
            firstContact.copy(phoneNumbers = allNumbers)
        }
}

