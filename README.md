# Contacts Provider (Kotlin Multiplatform)

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-blue.svg)](https://kotlinlang.org/)  
[![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-purple.svg)](https://www.jetbrains.com/lp/compose/)  

A **Kotlin Multiplatform library** to fetch user contacts from **Android** and **iOS** with a **Compose-friendly API**. Provides names, initials, phone numbers, and avatars, with minimal setup.

---

## Features

- Fetch contacts on **Android** (requires context) and **iOS** (no context required).  
- Provides `Contact` data class with:
  - `displayName`
  - `initials`
  - `phoneNumbers`
  - `avatar` (`URI` or `ImageBitmap`)  
- **Compose-ready**: use `rememberContactsProvider()` directly in your composable screen.  
- Works seamlessly in **Kotlin Multiplatform projects**.  
- Minimal setup — no need to configure entry points manually.

---

## Installation

### Gradle

 [![Latest Release](https://img.shields.io/github/v/release/dev-bilal-azzam/contacts-provider?label=latest_version)](https://github.com/dev-bilal-azzam/contacts-provider/releases/latest)
```kotlin
dependencies {
    implementation("io.github.dev-bilal-azzam:contacts-provider:$latest_version")
}
```


# ✨ What’s New in [![Latest Release](https://img.shields.io/github/v/release/dev-bilal-azzam/contacts-provider?label=Latest_Release)](https://github.com/dev-bilal-azzam/contacts-provider/releases/latest)

### 1. Customizable Fields  
- Introduced **custom field selection** when fetching contacts.  
- Users can now specify exactly which fields they need:
  - **Phone numbers**  
  - **Names (first - last)**  
  - **Avatar **  
- This avoids unnecessary queries and improves performance when only a subset of data is required.  

```kotlin
contactsProvider.getAllContacts(
    fields = setOf(ContactField.ID, ContactField.FIRST_NAME)
)
```

---

### 2. 🚀 50× Performance Boost  
- Optimized how **phone numbers** are fetched
- Results:
  - Before: ~10 seconds for large contact lists with phone numbers.  
  - After: ~200 ms (same speed as fetching names only).  

---

## 📊 Benefits
- **Faster**: Large contact lists now load up to **50× faster** when phone numbers are included.  
- **Flexible**: Apps can now fetch only the fields they actually need.  
---

## Usage

### Compose-friendly API
```kotlin
@Composable
fun ContactsScreen() {
    val contactsProvider = rememberContactsProvider()
    var contacts by remember { mutableStateOf<List<Contact>>(emptyList()) }

    LaunchedEffect(Unit) {
        // Ensure permission is granted
        contacts = contactsProvider.getAllContacts(
            fields = setOf(
                ContactField.ID,
                ContactField.FIRST_NAME,
                ContactField.PHONE_NUMBERS
            )
        )
    }

    LazyColumn {
        items(contacts) { contact ->
            Text(text = contact.displayName)
        }
    }
}

```

## Needed Permissions

### Android
```xml
<uses-permission android:name="android.permission.READ_CONTACTS" />
```

### IOS
```xml
<key>NSContactsUsageDescription</key>
<string>We need access to your contacts to display them in the app.</string>
```

---

## License

This project is licensed under the **MIT License**.  

See the [LICENSE](LICENSE) file for details.

---
### 💻 Contributors

[![](https://contrib.rocks/image?repo=dev-bilal-azzam/MyContacts)](https://github.com/dev-bilal-azzam/MyContacts/graphs/contributors)

