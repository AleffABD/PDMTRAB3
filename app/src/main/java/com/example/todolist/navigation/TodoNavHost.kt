package com.example.todolist.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todolist.ui.feature.addedit.AddEditScreen
import com.example.todolist.ui.feature.list.ListScreen
import kotlinx.serialization.Serializable

@Serializable
object ListRoute

@Serializable
data class AddEditRoute(val id: Long? = null)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoNavHost(onLogout: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ToDo") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Sair")
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ListRoute,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<ListRoute> {
                ListScreen(
                    navigateToAddEditScreen = { id ->
                        navController.navigate(AddEditRoute(id = id))
                    }
                )
            }

            composable<AddEditRoute> { backStackEntry ->
                val addEditRoute = backStackEntry.toRoute<AddEditRoute>()
                AddEditScreen(
                    id = addEditRoute.id,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
