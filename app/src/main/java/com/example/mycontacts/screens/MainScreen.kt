package com.example.mycontacts.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycontacts.R
import com.example.mycontacts.viewmodel.ContactsViewModel
import com.example.mycontacts.viewmodel.PermissionStatus
import com.example.mycontacts.utils.MockData

@Composable
fun MainScreen(contactsViewModel: ContactsViewModel, onContactClicked: (String) -> Unit) {
    val contactsUiState by contactsViewModel.uiState.collectAsState()

    when (contactsUiState.permissionStatus) {
        PermissionStatus.GRANTED -> {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchBoxComponent(contactsUiState.searchBoxText) {
                    contactsViewModel.onSearchTap(it)
                }
                LazyColumn {
                    items(
                        items = contactsUiState.currentSearchList,
                        itemContent = {
                            ContactItem(contact = it, onItemClicked = onContactClicked)
                        }
                    )
                }
            }
        }
        PermissionStatus.DENIED -> {
            ErrorScreen()
        }
        else -> {}
    }
}

@Composable
private fun SearchBoxComponent(searchBoxText: String, onSearchChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchBoxText,
        label = {
            Text(text = stringResource(id = R.string.search_contacts))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null
            )
        },
        onValueChange = {
            onSearchChange(it)
        }, singleLine = true, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
@Preview(showBackground = true)
private fun MainScreenPreview() {
    val currentSearchList = MockData().getMockContacts()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBoxComponent("") { }
        LazyColumn {
            items(
                items = currentSearchList,
                itemContent = {
                    ContactItem(contact = it, onItemClicked = { })
                }
            )
        }
    }

}
