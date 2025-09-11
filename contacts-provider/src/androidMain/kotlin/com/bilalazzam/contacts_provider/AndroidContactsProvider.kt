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

            val cursor = resolver
                .query(
                    ContactsContract.Contacts.CONTENT_URI,
                    projection,
                    null,
                    null,
                    null
                )

            // return contacts
            cursor?.use {
                buildList {
                    while (it.moveToNext()) {
                        add(extractContact(it, resolver, fields))
                    }
                }

            } ?: emptyList()
        }

    private fun extractContact(
        cursor: Cursor,
        resolver: ContentResolver,
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
            phoneNumbers = if (ContactField.PHONE_NUMBERS in fields)
                getPhoneNumbers(resolver, id)
            else
                emptyList(),
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

    private fun getPhoneNumbers(resolver: ContentResolver, contactId: String?): List<String> {
        if (contactId != null) return emptyList()

        val numbers = mutableListOf<String>()
        resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                numbers.add(
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                )
            }
        }
        return numbers
    }
}