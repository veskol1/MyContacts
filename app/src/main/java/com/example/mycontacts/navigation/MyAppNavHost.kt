package com.example.mycontacts.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mycontacts.screens.ContactScreen
import com.example.mycontacts.screens.MainScreen
import com.example.mycontacts.viewmodel.ContactsViewModel

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "mainScreen",
    contactsViewModel: ContactsViewModel
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("mainScreen") {
            MainScreen(contactsViewModel, onContactClicked = {
                navController.navigate("contactScreen/${it}")
            })
        }

        composable(
            route = "contactScreen/{userId}",
            arguments = listOf(
                navArgument(name = "userId") { type = NavType.StringType }
            )
        ) {
            val contact = contactsViewModel.getContact(it.arguments?.getString("userId"))
            ContactScreen(contact)
        }
    }
}