
package com.example.aiconsciousness.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aiconsciousness.data.AppState

@Composable
fun ModelsScreen(appState: AppState) {
  var selected by remember { mutableStateOf("gpt-4o-mini") }
  Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text("Choose a model", style = MaterialTheme.typography.titleLarge)
    listOf("gpt-4o-mini","gpt-4o","gpt-4.1-mini").forEach { model ->
      ElevatedCard(onClick={ selected=model }) {
        ListItem(headlineText={ Text(model) }, supportingText={ if(selected==model) Text("Selected") })
      }
      Spacer(Modifier.height(4.dp))
    }
  }
}
