package com.example.mycontacts.model

import androidx.compose.ui.graphics.Color

data class Contact(
    val id: String,
    val name: String,
    val numbers: MutableSet<Phone>,
    val emails: MutableSet<Email> = mutableSetOf(),
    val imageUri: String?,
    val firstNameLetter: Char,
    var backgroundColor: Color = Color.Transparent,
)

data class Phone(
    val number: String,
    val type: String
)

data class Email(
    val address: String,
    val type: String
)

