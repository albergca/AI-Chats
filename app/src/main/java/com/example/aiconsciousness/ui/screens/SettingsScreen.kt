
package com.example.aiconsciousness.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aiconsciousness.data.AppState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreen(appState: AppState) {
  var rememberFlag by remember { mutableStateOf(true) }
  LaunchedEffect(Unit) { appState.remember.collectLatest { rememberFlag = it } }
  Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text("Settings", style=MaterialTheme.typography.titleLarge)
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
      Text("Remember conversation"); Switch(checked=rememberFlag, onCheckedChange={ rememberFlag = it })
    }
  }
}
