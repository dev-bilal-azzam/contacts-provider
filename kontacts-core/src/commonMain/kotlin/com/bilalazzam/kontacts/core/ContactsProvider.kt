package com.bilalazzam.kontacts.core

interface ContactsProvider {
    suspend fun getAllContacts(fields: Set<ContactField>): List<Contact>
}