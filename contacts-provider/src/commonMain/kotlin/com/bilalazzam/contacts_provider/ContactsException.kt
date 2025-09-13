package com.bilalazzam.contacts_provider

open class ContactsException(message: String): Exception(message)

class ContactsPermissionDeniedException(message: String = "Contacts permission denied") : ContactsException(message)

class FetchContactsFailedException(message: String = "Failed to fetch contacts") : ContactsException(message)