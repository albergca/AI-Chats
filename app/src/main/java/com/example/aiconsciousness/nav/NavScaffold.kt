package com.example.aiconsciousness.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavScaffold(nav: NavHostController, content: @Composable () -> Unit) {
  val route = nav.currentBackStackEntryAsState().value?.destination?.route
  Scaffold(
    topBar = { CenterAlignedTopAppBar(title = { Text("AI Consciousness") }) },
    bottomBar = {
      if (route != Dest.Setup.route) {
        NavigationBar {
          NavigationBarItem(
            selected = route == Dest.Chat.route,
            onClick = { nav.navigate(Dest.Chat.route) },
            icon = { Icon(Icons.Filled.Chat, contentDescription = null) },
            label = { Text("Chat") }
          )
          NavigationBarItem(
            selected = route == Dest.Models.route,
            onClick = { nav.navigate(Dest.Models.route) },
            icon = { Icon(Icons.Filled.Tune, contentDescription = null) },
            label = { Text("Models") }
          )
          NavigationBarItem(
            selected = route == Dest.Settings.route,
            onClick = { nav.navigate(Dest.Settings.route) },
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
