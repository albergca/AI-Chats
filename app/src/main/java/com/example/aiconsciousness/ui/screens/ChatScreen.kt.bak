
package com.example.aiconsciousness.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aiconsciousness.data.AppState

data class Message(val role:String, val text:String)

@Composable
fun ChatScreen(appState: AppState) {
  var input by remember { mutableStateOf("") }
  var messages by remember { mutableStateOf(listOf<Message>()) }
  Column(Modifier.fillMaxSize().padding(16.dp)) {
    LazyColumn(Modifier.weight(1f)) {
      items(messages) { m -> AssistChip(onClick={}, label={ Text("${m.role}: ${m.text}") }, modifier=Modifier.padding(vertical=4.dp)) }
    }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      OutlinedTextField(value=input, onValueChange={ input=it }, modifier=Modifier.weight(1f), placeholder={ Text("Type…") })
      Button(onClick={ if(input.isNotBlank()){ messages = messages + Message("You",input); messages = messages + Message("Assistant","Echo: "+input); input="" } }) { Text("Send") }
    }
  }
}
