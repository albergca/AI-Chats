package com.example.aiconsciousness.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aiconsciousness.data.AppState
import com.example.aiconsciousness.data.ChatMessage

@Composable
fun ChatScreen(appState: AppState) {
  var text by remember { mutableStateOf("") }
  Column(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
    LazyColumn(Modifier.weight(1f)) {
      items(appState.messages) { msg ->
        MessageRow(msg)
        Spacer(Modifier.height(6.dp))
      }
    }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      OutlinedTextField(value = text, onValueChange = { text = it }, modifier = Modifier.weight(1f), singleLine = true, label = { Text("Type a message…") })
      Button(enabled = text.isNotBlank(), onClick = {
        val p = text.trim(); text = ""
        appState.sendUserMessage(p)
      }) { Text("Send") }
    }
  }
}

@Composable
private fun MessageRow(msg: ChatMessage) {
  val color = when (msg.from) {
    "You" -> Color(0xFF38BDF8)
    "OpenAI" -> Color(0xFF10B981)
    "Claude" -> Color(0xFFF97316)
    "Gemini" -> Color(0xFFA855F7)
    "Grok" -> Color(0xFF64748B)
    else -> Color(0xFF94A3B8)
  }
  ElevatedCard {
    Column(Modifier.padding(12.dp)) {
      Text(msg.from, color = color, style = MaterialTheme.typography.labelLarge)
      Spacer(Modifier.height(4.dp))
      Text(msg.text)
    }
  }
}
