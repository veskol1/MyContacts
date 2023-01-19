package com.example.mycontacts.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycontacts.model.Contact
import com.example.mycontacts.ui.theme.CardColor
import com.example.mycontacts.utils.MockData

@Composable
fun ContactItem(contact: Contact, onItemClicked: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClicked(contact.id) }
            .background(color = CardColor, shape = RoundedCornerShape(20.dp)),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .height(50.dp)
                .clip(CircleShape)
        ) {
            ImageComponent(contact = contact)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = contact.name, modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically)
                    .padding(6.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ContactItemPreview() {
    ContactItem(contact = MockData().getMockContact()) {}
}