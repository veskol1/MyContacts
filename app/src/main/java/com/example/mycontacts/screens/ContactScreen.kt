package com.example.mycontacts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycontacts.model.Phone
import com.example.mycontacts.R
import com.example.mycontacts.model.Contact
import com.example.mycontacts.model.Email
import com.example.mycontacts.ui.theme.BackgroundColor
import com.example.mycontacts.ui.theme.CardColor
import com.example.mycontacts.utils.MockData

@Composable
fun ContactScreen(contact: Contact?) {
    if (contact != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundColor)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center, modifier = Modifier
                    .align(CenterHorizontally)
                    .wrapContentSize()
                    .padding(30.dp)
            ) {
                ImageComponent(contact, circleSize = 200.dp, circleFontSize = 50.sp)
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 20.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(text = contact.name, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                backgroundColor = CardColor,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = stringResource(id = R.string.contact_info_title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Phones(numbers = contact.numbers)
                    Spacer(modifier = Modifier.height(10.dp))
                    if (contact.emails.isNotEmpty()) {
                        Emails(emails = contact.emails)
                    }
                }
            }
        }
    }

}

@Composable
fun Phones(numbers: MutableSet<Phone>) {
    Text(
        text = stringResource(id = R.string.phones_title),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp)
    )
    numbers.forEach {
        PhoneRow(phone = it)
    }
}

@Composable
fun PhoneRow(phone: Phone) {
    Row(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_phone),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier.align(CenterVertically)
        )
        Column {
            Text(
                text = phone.number,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            Text(
                text = phone.type,
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
        }
    }
}

@Composable
fun Emails(emails: MutableSet<Email>) {
    Text(
        text = stringResource(id = R.string.emails_title),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp)
    )
    emails.forEach {
        EmailRow(email = it)
    }
}

@Composable
fun EmailRow(email: Email) {
    Row(modifier = Modifier.padding(6.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_email),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier.align(CenterVertically)
        )
        Column() {
            Text(
                text = email.address,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            Text(
                text = email.type,
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)

            )
        }
    }
}


@Composable
@Preview
private fun ContactScreenPreview() {
    ContactScreen(MockData().getMockContact())
}
