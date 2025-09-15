package com.bilalazzam.contacts_provider.utils

import com.google.i18n.phonenumbers.PhoneNumberUtil

fun removeCountryCode(phone: String): String {
    val phoneInstance = PhoneNumberUtil.getInstance()
    return try {
        phoneInstance.parse(phone, null).nationalNumber.toString()
    } catch (_: Exception) {
        phone
    }
}