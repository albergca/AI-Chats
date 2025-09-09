package com.example.aiconsciousness.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aiconsciousness.data.AppState
import com.example.aiconsciousness.ui.screens.*

enum class Dest(val route: String) { Setup("setup"), Chat("chat"), Models("models"), Settings("settings") }

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
  val ctx = LocalContext.current
  val appState = remember { AppState(ctx) }
  val start = if (appState.needsSetup()) Dest.Setup.route else Dest.Chat.route
  NavScaffold(navController) {
    NavHost(navController = navController, startDestination = start) {
      composable(Dest.Setup.route) {
        SetupScreen(appState) {
          navController.navigate(Dest.Chat.route) { popUpTo(Dest.Setup.route) { inclusive = true } }
        }
      }
      composable(Dest.Chat.route) { ChatScreen(appState) }
      composable(Dest.Models.route) { ModelsScreen(appState) }
      composable(Dest.Settings.route) { SettingsScreen(appState) }
    }
  }
}
