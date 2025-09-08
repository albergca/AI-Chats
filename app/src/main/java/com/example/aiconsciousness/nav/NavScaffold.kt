
package com.example.aiconsciousness.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavScaffold(navController: NavHostController, content: @Composable () -> Unit) {
  val items = Dest.values()
  Scaffold(
    topBar = { TopAppBar(title = { Text("AI Consciousness") }) },
    bottomBar = {
      NavigationBar {
        val backStack by navController.currentBackStackEntryAsState()
        val current = backStack?.destination?.route
        items.forEach { dest ->
          NavigationBarItem(
            selected = current == dest.route,
            onClick = {
              navController.navigate(dest.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true; restoreState = true
              }
            },
            icon = { Icon(dest.icon, contentDescription = dest.label) },
            label = { Text(dest.label) }
          )
        }
      }
    }
  ) { inner -> Surface(Modifier.padding(inner)) { content() } }
}
