package com.bilalazzam.contacts_provider

interface ContactsProvider {
    suspend fun getAllContacts(fields: Set<ContactField>): List<Contact>
}