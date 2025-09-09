package com.example.aiconsciousness.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aiconsciousness.data.AppState

@Composable
fun ModelsScreen(appState: AppState) {
  var selected by remember { mutableStateOf("gpt-4o-mini") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Text("Choose a model", style = MaterialTheme.typography.titleLarge)

    listOf("gpt-4o-mini", "gpt-4o", "gpt-4.1-mini").forEach { model ->
      ElevatedCard(onClick = { selected = model }) {
        ListItem(
          headlineContent = { Text(model) },
          supportingContent = if (selected == model) {
            { Text("Selected") }
          } else null
          // optional: leadingContent / trailingContent
        )
      }
      Spacer(Modifier.height(4.dp))
    }
  }
}
