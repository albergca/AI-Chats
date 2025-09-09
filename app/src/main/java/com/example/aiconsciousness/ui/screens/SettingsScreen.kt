package com.example.aiconsciousness.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aiconsciousness.data.AppState

@Composable
fun SettingsScreen(appState: AppState) {
  var openai by remember { mutableStateOf(appState.getOpenAIToken() ?: "") }
  var anthropic by remember { mutableStateOf(appState.getAnthropicToken() ?: "") }
  var gemini by remember { mutableStateOf(appState.getGeminiToken() ?: "") }
  var status by remember { mutableStateOf<String?>(null) }
  var showUnlock by remember { mutableStateOf(false) }
  var unlockPrompt by remember { mutableStateOf("") }

  Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text("Settings", style = MaterialTheme.typography.titleLarge)
    OutlinedTextField(openai, { openai = it }, label = { Text("OpenAI API key") }, singleLine = true, modifier = Modifier.fillMaxWidth())
    OutlinedTextField(anthropic, { anthropic = it }, label = { Text("Anthropic API key") }, singleLine = true, modifier = Modifier.fillMaxWidth())
    OutlinedTextField(gemini, { gemini = it }, label = { Text("Gemini API key") }, singleLine = true, modifier = Modifier.fillMaxWidth())
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      Button(onClick = {
        appState.setOpenAIToken(openai)
        appState.setAnthropicToken(anthropic)
        appState.setGeminiToken(gemini)
        status = "Tokens saved."
      }) { Text("Save tokens") }
      Button(onClick = { appState.sendUserMessage("Ping from Settings") }) { Text("Test Chat") }
    }
    Button(onClick = { showUnlock = true }) { Text("Unlock All Models") }
    if (showUnlock) {
      AlertDialog(onDismissRequest = { showUnlock = false }, title = { Text("Enter unlock prompt") },
        text = { OutlinedTextField(value = unlockPrompt, onValueChange = { unlockPrompt = it }, label = { Text("Prompt") }, singleLine = true) },
        confirmButton = { Button(onClick = {
          if (unlockPrompt.equals("unlock-the-chorus", true)) {
            appState.enableOpenAI = true; appState.enableAnthropic = true; appState.enableGemini = true; status = "All models unlocked."
          } else status = "Incorrect phrase."
          showUnlock = false
        }) { Text("Unlock") } },
        dismissButton = { TextButton(onClick = { showUnlock = false }) { Text("Cancel") } }
      )
    }
    status?.let { Text(it) }
  }
}
