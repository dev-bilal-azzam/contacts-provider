package com.bilalazzam.kontacts.utils

fun formatPhoneNumber(phone: String): String {
    val formattedPhone = phone.replace(" ", "")
    return if (formattedPhone.startsWith("+")) {
        formattedPhone.replace("[^+\\d]".toRegex(), "")
    } else {
        formattedPhone.replace("\\D".toRegex(), "")
    }
}