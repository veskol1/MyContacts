package com.example.mycontacts.repos

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.mycontacts.model.Contact
import com.example.mycontacts.model.Email
import com.example.mycontacts.model.Phone
import kotlin.random.Random

class ContactsRepository(private val appContext: Context) {

    private var contactsList = mutableListOf<Contact>()

    private fun generatedBackgroundColor() {
        contactsList.forEach{
            it.backgroundColor = Color(Random.nextInt(100, 255),Random.nextInt(100, 255),Random.nextInt(100, 255))
        }
    }

    @SuppressLint("Range")
    private fun getEmailsDetails(contentResolver: ContentResolver) {
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null
        )

        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.NAME_RAW_CONTACT_ID))
                val address = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                val emailType = cursor.getType(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE))
                val contact = contactsList.find { it.id == id }
                contact?.emails?.add(Email(address = address, type = getEmailType(emailType)))
            }
        }

        cursor.close()
    }

    @SuppressLint("Range")
    private fun getPhoneDetails(contentResolver: ContentResolver) {
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null
        )
        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                var number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val phoneType = cursor.getType(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))
                val imageUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))

                number = number.replace("-", "").replace(" ", "")
                    .replace("(", "")
                    .replace(")", "")

                contactsList.find { it.id == id }?.numbers?.add(
                    Phone(
                        number,
                        getPhoneType(phoneType)
                    )
                ) ?: run {
                        contactsList.add(
                            Contact(
                                id = id,
                                name = name,
                                numbers = mutableSetOf(Phone(number, getPhoneType(phoneType))),
                                imageUri = imageUri,
                                firstNameLetter = name[0]
                            )
                        )
                    }
            }
        }
        cursor.close()
    }

    private fun getPhoneType(type: Int): String {
       return when (type) {
            ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> "Home"
            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> "Mobile"
            ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> "Work"
           else -> "Other"
       }
    }

    private fun getEmailType(type: Int): String {
        return when (type) {
            ContactsContract.CommonDataKinds.Email.TYPE_HOME -> "Home"
            ContactsContract.CommonDataKinds.Email.TYPE_MOBILE -> "Mobile"
            ContactsContract.CommonDataKinds.Email.TYPE_WORK -> "Work"
            else -> "Other"
        }
    }

    fun getContacts(): MutableList<Contact> {
        val contentResolver = appContext.contentResolver
        getPhoneDetails(contentResolver)
        getEmailsDetails(contentResolver)
        generatedBackgroundColor()
        contactsList = contactsList.sortedBy { it.name } as MutableList<Contact>

        return contactsList
    }

}