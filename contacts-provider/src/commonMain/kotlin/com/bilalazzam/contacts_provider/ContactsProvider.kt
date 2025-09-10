package com.bilalazzam.contacts_provider

interface ContactsProvider {
    suspend fun getAllContacts(): List<Contact>
}