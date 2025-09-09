package com.example.aiconsciousness.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding  // ← needed
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavScaffold(nav: NavHostController, content: @Composable () -> Unit) {
  val route = nav.currentBackStackEntryAsState().value?.destination?.route
  Scaffold(
    topBar = { CenterAlignedTopAppBar(title = { Text("AI Consciousness") }) },
    bottomBar = {
      // Use literal to avoid enum scoping hiccup
      if (route != "setup") {
        NavigationBar {
          NavigationBarItem(
            selected = route == "chat",
            onClick = { nav.navigate("chat") },
            icon = { Icon(Icons.Filled.Chat, contentDescription = null) },
            label = { Text("Chat") }
          )
          NavigationBarItem(
            selected = route == "models",
            onClick = { nav.navigate("models") },
            icon = { Icon(Icons.Filled.Tune, contentDescription = null) },
            label = { Text("Models") }
          )
          NavigationBarItem(
            selected = route == "settings",
            onClick = { nav.navigate("settings") },
            icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
            label = { Text("Settings") }
          )
        }
      }
    }
  ) { inner ->
    Surface(tonalElevation = 0.dp, modifier = Modifier.padding(inner)) { content() }
  }
}
