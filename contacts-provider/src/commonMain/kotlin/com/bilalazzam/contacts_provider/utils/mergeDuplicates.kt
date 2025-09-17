package com.bilalazzam.contacts_provider.utils

import com.bilalazzam.contacts_provider.Contact

fun List<Contact>.mergeDuplicates(): List<Contact> {
    return this
        .groupBy { it.displayName }
        .map { (_, group) ->
            val allNumbers = group.flatMap { it.phoneNumbers }.distinct()
            val firstContact = group.first()
            firstContact.copy(phoneNumbers = allNumbers)
        }
}

