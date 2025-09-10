# Contacts Provider (Kotlin Multiplatform)

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org/)  
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
```kotlin
dependencies {
    implementation("io.github.dev-bilal-azzam:contacts-provider:$latest_version")
}
```
You can find Latest Version Here [![Latest Release](https://img.shields.io/github/v/release/dev-bilal-azzam/contacts-provider?label=latest)](https://github.com/dev-bilal-azzam/contacts-provider/releases/latest)


## Usage

### Compose-friendly API
```kotlin
@Composable
fun ContactsScreen() {
    val contactsProvider = rememberContactsProvider()
    var contacts by remember { mutableStateOf<List<Contact>>(emptyList()) }

    LaunchedEffect(Unit) {
        // Ensure permission is granted
        contacts = contactsProvider.getAllContacts()
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

