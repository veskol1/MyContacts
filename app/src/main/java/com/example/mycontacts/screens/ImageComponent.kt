package com.example.mycontacts.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mycontacts.model.Contact

@Composable
fun ImageComponent(contact: Contact, circleSize: Dp = 45.dp, circleFontSize: TextUnit = 20.sp) {
    if (contact.imageUri != null) {
        AsyncImage(model = contact.imageUri, contentDescription = null, modifier = Modifier
            .clip(CircleShape)
            .size(circleSize)
        )
    } else {
        Box(
            modifier = Modifier
                .size(circleSize)
                .clip(CircleShape)
                .background(color = contact.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(text = contact.firstNameLetter.toString(), fontSize = circleFontSize)
        }
    }


}