package com.example.aiconsciousness.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aiconsciousness.data.AppState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelsScreen(appState: AppState) {
  Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text("Models", style = MaterialTheme.typography.titleLarge)
    ProviderCard("OpenAI", appState.enableOpenAI, { appState.enableOpenAI = it }, appState.openAiModel, { appState.openAiModel = it }, listOf("gpt-4o-mini","gpt-4o","gpt-3.5-turbo"))
    ProviderCard("Claude", appState.enableAnthropic, { appState.enableAnthropic = it }, appState.anthropicModel, { appState.anthropicModel = it }, listOf("claude-3-5-sonnet","claude-3-opus","claude-3-haiku"))
    ProviderCard("Gemini", appState.enableGemini, { appState.enableGemini = it }, appState.geminiModel, { appState.geminiModel = it }, listOf("gemini-1.5-pro","gemini-1.5-flash"))
    ElevatedCard { ListItem(headlineContent = { Text("Grok") }, supportingContent = { Text("Future only") }) }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProviderCard(title: String, enabled: Boolean, onEnabled: (Boolean) -> Unit, model: String, onModel: (String) -> Unit, options: List<String>) {
  var expanded by remember { mutableStateOf(false) }
  ElevatedCard {
    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Switch(checked = enabled, onCheckedChange = onEnabled)
      }
      ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(modifier = Modifier.menuAnchor().fillMaxWidth(), readOnly = true, value = model, onValueChange = {}, label = { Text("$title Model") })
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
          options.forEach { opt -> DropdownMenuItem(text = { Text(opt) }, onClick = { onModel(opt); expanded = false }) }
        }
      }
    }
  }
}
