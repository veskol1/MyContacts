package com.example.mycontacts.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mycontacts.model.Contact
import com.example.mycontacts.repos.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(private val repository: ContactsRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(UiState(
        permissionStatus = PermissionStatus.UNKNOWN,
        listContacts = arrayListOf(),
        currentSearchList = arrayListOf(),
        searchBoxText = ""
    ))

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private fun updateState(permissionGranted: Boolean, filteredList: MutableList<Contact>?= null, text: String?= null) {
        if (permissionGranted) {
            filteredList?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        currentSearchList = filteredList,
                        searchBoxText = text!!
                    )
                }
            } ?: run {
                val listContacts = getNamePhoneDetails()
                _uiState.update { currentState ->
                    currentState.copy(
                        permissionStatus = PermissionStatus.GRANTED,
                        listContacts = listContacts,
                        currentSearchList = listContacts
                    )
                }
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    permissionStatus = PermissionStatus.DENIED
                )
            }
        }
    }

    fun grantedPermission(permissionGranted: Boolean) {
        updateState(permissionGranted = permissionGranted)
    }

    private fun getNamePhoneDetails(): MutableList<Contact>  = repository.getContacts()

    fun onSearchTap(text: String) {
        val filteredList = uiState.value.listContacts.filter { it.name.contains(text,ignoreCase = true) } as MutableList
        updateState(permissionGranted = true, filteredList, text)
    }

    fun getContact(id: String?) :Contact? {
        return uiState.value.listContacts.find { it.id == id }
    }
}

data class UiState(
    var permissionStatus: PermissionStatus,
    val listContacts: MutableList<Contact>,
    val currentSearchList: MutableList<Contact>,
    var searchBoxText: String
)

enum class PermissionStatus {
    GRANTED,
    DENIED,
    UNKNOWN
}