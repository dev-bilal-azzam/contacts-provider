package com.bilalazzam.contacts_provider

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AndroidContactsProvider(private val context: Context) : ContactsProvider {

    private val fieldProjections = mapOf(
        ContactField.ID to ContactsContract.Contacts._ID,
        ContactField.FIRST_NAME to ContactsContract.Contacts.DISPLAY_NAME,
        ContactField.LAST_NAME to ContactsContract.Contacts.DISPLAY_NAME,
        ContactField.AVATAR to ContactsContract.Contacts.PHOTO_URI,
        ContactField.PHONE_NUMBERS to ContactsContract.Contacts.HAS_PHONE_NUMBER
    )

    override suspend fun getAllContacts(fields: Set<ContactField>): List<Contact> =
        withContext(Dispatchers.IO) {
            val resolver = context.contentResolver
            val projection = fields.mapNotNull { fieldProjections[it] }.toTypedArray()
            val phoneNumbersMap = if (ContactField.PHONE_NUMBERS in fields) {
                getAllPhoneNumbers(resolver)
            } else {
                emptyMap()
            }

            val cursor = resolver
                .query(
                    ContactsContract.Contacts.CONTENT_URI,
                    projection,
                    null,
                    null,
                    null
                )

            // return contacts
            cursor?.use { cursor ->
                buildList {
                    while (cursor.moveToNext()) {
                        val contact = extractContact(cursor, fields)
                        val contactWithPhoneNumbers = contact.copy(
                            phoneNumbers = phoneNumbersMap[contact.id] ?: emptyList()
                        )
                        add(contactWithPhoneNumbers)
                    }
                }
            } ?: emptyList()
        }

    private fun extractContact(
        cursor: Cursor,
        fields: Set<ContactField>
    ): Contact {
        val id = getValue(cursor, ContactField.ID, fields)
        val displayName = getValue(cursor, ContactField.FIRST_NAME, fields)
        val photoUri = getValue(cursor, ContactField.AVATAR, fields)

        val nameParts = displayName?.trim()?.split("\\s+".toRegex())

        return Contact(
            id = id,
            firstName = nameParts?.firstOrNull(),
            lastName = nameParts?.drop(1)?.joinToString(" "),
            avatar = if (photoUri != null)
                ContactAvatar.AvatarUri(photoUri)
            else
                ContactAvatar.None
        )
    }

    private fun getValue(cursor: Cursor, field: ContactField, fields: Set<ContactField>): String? {
        val column = fieldProjections[field] ?: return null
        if (field !in fields) return null
        return cursor.getString(cursor.getColumnIndexOrThrow(column))
    }

    private fun getAllPhoneNumbers(resolver: ContentResolver): Map<String, List<String>> {
        val numbersByContact = mutableMapOf<String, MutableList<String>>()

        resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null,
            null,
            null
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val numberIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (cursor.moveToNext()) {
                val contactId = cursor.getString(idIndex)
                val number = cursor.getString(numberIndex)

                numbersByContact.getOrPut(contactId) { mutableListOf() }.add(number)
            }
        }

        return numbersByContact
    }
}