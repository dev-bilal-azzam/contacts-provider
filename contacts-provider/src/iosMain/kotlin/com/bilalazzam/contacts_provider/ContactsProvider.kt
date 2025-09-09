package com.bilalazzam.contacts_provider


import kotlinx.cinterop.ExperimentalForeignApi
import platform.Contacts.*
import platform.UIKit.UIImage

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ContactsProvider {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getAllContacts(): List<Contact> {
        val store = CNContactStore()
        val keysToFetch = listOf(
            CNContactIdentifierKey,
            CNContactGivenNameKey,
            CNContactFamilyNameKey,
            CNContactPhoneNumbersKey,
            CNContactImageDataKey
        )
        val request = CNContactFetchRequest(keysToFetch = keysToFetch)
        val contacts = mutableListOf<Contact>()

        store.enumerateContactsWithFetchRequest(request, error = null) { cnContact, _ ->
            val firstName = cnContact?.givenName ?: ""
            val lastName = cnContact?.familyName ?: ""
            val numbers = cnContact?.getPhoneNumbers() ?: emptyList()
            val imageData = cnContact?.imageData
            val avatar = if (imageData != null)
                ContactAvatar.AvatarBitmap(UIImage(data = imageData).toImageBitmap())
            else
                ContactAvatar.None

            contacts.add(
                Contact(
                    id = cnContact?.identifier,
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumbers = numbers,
                    avatar = avatar
                )
            )
        }

        return contacts
    }

    private fun CNContact.getPhoneNumbers(): List<String> {
        return phoneNumbers.mapNotNull { labeledValue ->
            (labeledValue as? CNLabeledValue)?.value.let { it as? CNPhoneNumber }?.stringValue
        }
    }
}