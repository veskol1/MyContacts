package com.example.mycontacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.example.mycontacts.navigation.MyAppNavHost
import com.example.mycontacts.ui.theme.MyContactsTheme
import com.example.mycontacts.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val contactsViewModel: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndGrantPermission()

        setContent {
            MyContactsTheme {
                MyAppNavHost(contactsViewModel = contactsViewModel, modifier = Modifier.background(
                    Color.White))
            }
        }
    }

    private fun checkAndGrantPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    contactsViewModel.grantedPermission(permissionGranted = true)
                } else {
                    contactsViewModel.grantedPermission(permissionGranted = false)
                }
            }

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) -> {
                contactsViewModel.grantedPermission(permissionGranted = true)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }
}
