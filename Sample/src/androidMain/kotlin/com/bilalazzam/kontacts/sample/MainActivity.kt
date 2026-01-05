package com.bilalazzam.kontacts.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bilalazzam.kontacts.sample.ContactsScreen
import com.bilalazzam.kontacts.sample.di.Dependencies
import com.bilalazzam.kontacts.sample.di.dependencies

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        dependencies = Dependencies(this)
        setContent {
            ContactsScreen()
        }
    }
}
