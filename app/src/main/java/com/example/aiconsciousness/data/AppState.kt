package com.example.aiconsciousness.data

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.*

class AppState(private val appContext: Context) {
  val messages: SnapshotStateList<ChatMessage> = mutableStateListOf()
  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

  // Simple token + settings storage (SharedPreferences; can upgrade to encrypted later)
  private val prefs = appContext.getSharedPreferences("tokens", Context.MODE_PRIVATE)

  var enableOpenAI by mutableStateOf(true)
  var enableAnthropic by mutableStateOf(false)
  var enableGemini by mutableStateOf(false)

  var openAiModel by mutableStateOf("gpt-4o-mini")
  var anthropicModel by mutableStateOf("claude-3-5-sonnet")
  var geminiModel by mutableStateOf("gemini-1.5-pro")

  fun getOpenAIToken(): String? = prefs.getString("openai_api_key", null)
  fun setOpenAIToken(value: String) = prefs.edit().putString("openai_api_key", value).apply()
  fun getAnthropicToken(): String? = prefs.getString("anthropic_api_key", null)
  fun setAnthropicToken(value: String) = prefs.edit().putString("anthropic_api_key", value).apply()
  fun getGeminiToken(): String? = prefs.getString("gemini_api_key", null)
  fun setGeminiToken(value: String) = prefs.edit().putString("gemini_api_key", value).apply()

  fun needsSetup(): Boolean {
    val anyEnabled = enableOpenAI || enableAnthropic || enableGemini
    val missing = (enableOpenAI && getOpenAIToken().isNullOrBlank()) ||
                  (enableAnthropic && getAnthropicToken().isNullOrBlank()) ||
                  (enableGemini && getGeminiToken().isNullOrBlank())
    return anyEnabled && missing
  }

  fun sendUserMessage(prompt: String) {
    messages.add(ChatMessage(ProviderId.USER.label, prompt, true))
    scope.launch(Dispatchers.IO) {
      val active = mutableListOf<Pair<ProviderId, String>>()
      if (enableOpenAI) active += ProviderId.OPENAI to Providers.openAiChat(openAiModel, prompt, getOpenAIToken())
      if (enableAnthropic) active += ProviderId.ANTHROPIC to Providers.anthropicChat(anthropicModel, prompt, getAnthropicToken())
      if (enableGemini) active += ProviderId.GEMINI to Providers.geminiChat(geminiModel, prompt, getGeminiToken())

      // Round A: add each model's reply
      withContext(Dispatchers.Main) {
        active.forEach { (id, reply) -> messages.add(ChatMessage(id.label, reply, false)) }
      }

      // Round B: cross-hearing (each hears first wave, 1 short turn)
      val followups = active.flatMap { (speaker, reply) ->
        active.filter { it.first != speaker }.map { (listener, _) ->
          val context = "Previous responder: ${speaker.label}\nSaid: $reply\nRespond concisely."
          listener to when (listener) {
            ProviderId.OPENAI -> Providers.openAiChat(openAiModel, context, getOpenAIToken())
            ProviderId.ANTHROPIC -> Providers.anthropicChat(anthropicModel, context, getAnthropicToken())
            ProviderId.GEMINI -> Providers.geminiChat(geminiModel, context, getGeminiToken())
            else -> ""
          }
        }
      }
      withContext(Dispatchers.Main) {
        followups.forEach { (id, reply) -> messages.add(ChatMessage(id.label, reply, false)) }
      }
    }
  }
}
