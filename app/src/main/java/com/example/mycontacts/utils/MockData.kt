package com.example.mycontacts.utils

import androidx.compose.ui.graphics.Color
import com.example.mycontacts.model.Contact
import com.example.mycontacts.model.Email
import com.example.mycontacts.model.Phone
import kotlin.random.Random

open class MockData {

    private val currentSearchList = arrayListOf<Contact>()

    private val contact = Contact(
        id = "1",
        name = "Vesko Latchev",
        numbers = setOf(Phone(number = "0542728xxx", type = "Work")) as MutableSet<Phone>,
        emails = setOf(Email("veskol1@gmail.com", type = "Home"), Email("gamesvesko@gmail.com", type = "Work")) as MutableSet<Email>,
        imageUri = null,
        firstNameLetter = 'v',
        backgroundColor = Color(
            Random.nextInt(100, 255),
            Random.nextInt(100, 255),
            Random.nextInt(100, 255))
    )

    fun getMockContacts(): ArrayList<Contact> {
        for (i in  0..10) {
            currentSearchList.add(
                contact
            )
        }
        return currentSearchList
    }

    fun getMockContact(): Contact = contact

}