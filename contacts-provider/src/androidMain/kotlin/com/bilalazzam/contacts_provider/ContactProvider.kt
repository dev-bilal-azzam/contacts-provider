package com.bilalazzam.contacts_provider

import android.content.Context
import android.provider.ContactsContract


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ContactsProvider(private val context: Context) {
    actual suspend fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val resolver = context.contentResolver

        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_URI,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
        )

        val cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection, null, null, null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val displayName =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                        ?: ""
                val photoUri =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI))

                val nameParts = displayName.trim().split("\\s+".toRegex())
                val firstName = nameParts.firstOrNull() ?: ""
                val lastName = nameParts.drop(1).joinToString(" ")

                val phoneNumbers = mutableListOf<String>()
                if (it.getInt(it.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val phoneCursor = resolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                        arrayOf(id),
                        null
                    )
                    phoneCursor?.use { pc ->
                        while (pc.moveToNext()) {
                            val number = pc.getString(
                                pc.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                            phoneNumbers.add(number)
                        }
                    }
                }

                val avatar = if (photoUri != null) ContactAvatar.AvatarUri(photoUri) else ContactAvatar.None

                contacts.add(
                    Contact(
                        id = id,
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumbers = phoneNumbers,
                        avatar = avatar
                    )
                )
            }
        }
        return contacts
    }
}