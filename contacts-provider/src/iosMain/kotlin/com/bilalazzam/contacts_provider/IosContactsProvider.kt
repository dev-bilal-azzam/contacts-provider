package com.bilalazzam.contacts_provider


import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import platform.Contacts.*
import platform.UIKit.UIImage

class IosContactsProvider: ContactsProvider {

    private val fieldProjections = mapOf(
        ContactField.ID to CNContactIdentifierKey,
        ContactField.FIRST_NAME to CNContactGivenNameKey,
        ContactField.LAST_NAME to CNContactFamilyNameKey,
        ContactField.AVATAR to CNContactImageDataKey,
        ContactField.PHONE_NUMBERS to CNContactPhoneNumbersKey
    )

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getAllContacts(fields: Set<ContactField>): List<Contact> = withContext(Dispatchers.IO) {
        val store = CNContactStore()
        val keysToFetch = fields.mapNotNull { fieldProjections[it] }.toList()
        val request = CNContactFetchRequest(keysToFetch = keysToFetch)

        // return contacts
        buildList {
            store.enumerateContactsWithFetchRequest(request, error = null) { cnContact, _ ->
                cnContact?.let {
                    add(
                        Contact(
                            id = getValue(cnContact, ContactField.ID, fields),
                            firstName = getValue(cnContact, ContactField.FIRST_NAME, fields),
                            lastName = getValue(cnContact, ContactField.LAST_NAME, fields),
                            phoneNumbers = cnContact.getPhoneNumbers(),
                            avatar = getAvatar(cnContact, fields)
                        )
                    )
                }
            }
        }

    }

    private fun CNContact.getPhoneNumbers(): List<String> {
        return phoneNumbers.mapNotNull { labeledValue ->
            (labeledValue as? CNLabeledValue)?.value.let { it as? CNPhoneNumber }?.stringValue
        }
    }

    private fun getValue(cnContact: CNContact, field: ContactField, fields: Set<ContactField>): String? {
        if (field !in fields) return null
        return when (field) {
            ContactField.ID -> cnContact.identifier
            ContactField.FIRST_NAME -> cnContact.givenName
            ContactField.LAST_NAME -> cnContact.familyName
            else -> null
        }
    }

    private fun getAvatar(cnContact: CNContact, fields: Set<ContactField>): ContactAvatar {
        if (ContactField.AVATAR !in fields) return ContactAvatar.None
        val imageData = cnContact.imageData
        return if (imageData != null)
            ContactAvatar.AvatarBitmap(UIImage(data = imageData).toImageBitmap())
        else
            ContactAvatar.None
    }
}