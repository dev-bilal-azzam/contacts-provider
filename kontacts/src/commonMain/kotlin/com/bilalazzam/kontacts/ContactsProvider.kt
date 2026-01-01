package com.bilalazzam.kontacts

interface ContactsProvider {
    suspend fun getAllContacts(fields: Set<ContactField>): List<Contact>
}