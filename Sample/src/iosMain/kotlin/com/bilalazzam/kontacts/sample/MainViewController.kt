package com.bilalazzam.kontacts.sample

import androidx.compose.ui.window.ComposeUIViewController
import com.bilalazzam.kontacts.sample.di.Dependencies
import com.bilalazzam.kontacts.sample.di.dependencies
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    dependencies = Dependencies()
    return ComposeUIViewController { ContactsScreen() }
}