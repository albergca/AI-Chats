
package com.example.aiconsciousness.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.*
import com.example.aiconsciousness.data.AppState
import com.example.aiconsciousness.ui.screens.*

enum class Dest(val route:String, val label:String, val icon: ImageVector) {
  Chat("chat","Chat",Icons.Default.Chat),
  Models("models","Models",Icons.Default.Tune),
  Settings("settings","Settings",Icons.Default.Settings)
}

@Composable
fun AppNavHost(appState: AppState, navController: NavHostController = rememberNavController()) {
  NavScaffold(navController) {
    NavHost(navController, startDestination = Dest.Chat.route) {
      composable(Dest.Chat.route) { ChatScreen(appState) }
      composable(Dest.Models.route) { ModelsScreen(appState) }
      composable(Dest.Settings.route) { SettingsScreen(appState) }
    }
  }
}
