package com.bilalazzam.contacts_provider

import androidx.compose.ui.graphics.ImageBitmap


data class Contact(
    val id: String?,
    val firstName: String?,
    val lastName: String?,
    val phoneNumbers: List<String>,
    val avatar: ContactAvatar = ContactAvatar.None
) {
    val displayName: String
        get() = when {
            !firstName.isNullOrBlank() && !lastName.isNullOrBlank() -> "$firstName $lastName"
            !firstName.isNullOrBlank() -> firstName
            !lastName.isNullOrBlank() -> lastName
            else -> "Unknown"
        }

    val initials: String
        get() = when {
            !firstName.isNullOrBlank() && !lastName.isNullOrBlank() ->
                "${firstName.firstOrNull()}${lastName.firstOrNull()}".uppercase()
            !firstName.isNullOrBlank() ->
                firstName.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
            !lastName.isNullOrBlank() ->
                lastName.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
            else -> "?"
        }

    val hasPhoneNumbers: Boolean
        get() = phoneNumbers.isNotEmpty()
}

sealed class ContactAvatar {
    data class AvatarUri(val uri: String): ContactAvatar()
    data class AvatarBitmap(val bitmap: ImageBitmap): ContactAvatar()
    data object None: ContactAvatar()

}