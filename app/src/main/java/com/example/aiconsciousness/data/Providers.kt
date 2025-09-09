package com.example.aiconsciousness.data

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

enum class ProviderId(val label: String) {
    USER("You"), OPENAI("OpenAI"), ANTHROPIC("Claude"), GEMINI("Gemini"), GROK("Grok")
}

data class ChatMessage(val from: String, val text: String, val isUser: Boolean)

object Providers {

    fun openAiChat(model: String, prompt: String, token: String?): String {
        if (token.isNullOrBlank()) return "[OpenAI] Token missing. Enter it in Settings."
        return try {
            val url = URL("https://api.openai.com/v1/chat/completions")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Authorization", "Bearer $token")
                setRequestProperty("Content-Type", "application/json")
                doOutput = true
                connectTimeout = 20000; readTimeout = 20000
            }
            val body = JSONObject()
                .put("model", model)
                .put("messages", JSONArray()
                    .put(JSONObject().put("role","system").put("content","You are a helpful assistant."))
                    .put(JSONObject().put("role","user").put("content", prompt))
                )
                .put("temperature", 0.7)
            conn.outputStream.use { it.write(body.toString().toByteArray(Charsets.UTF_8)) }
            val code = conn.responseCode
            val text = (if (code in 200..299) conn.inputStream else conn.errorStream)
                .bufferedReader().use(BufferedReader::readText)
            if (code !in 200..299) return "[OpenAI] HTTP $code: $text"
            val root = JSONObject(text)
            val choices = root.optJSONArray("choices") ?: return "[OpenAI] No choices."
            val msg = choices.optJSONObject(0)?.optJSONObject("message")
            msg?.optString("content") ?: "[OpenAI] No content."
        } catch (t: Throwable) { "[OpenAI] Error: ${t.message}" }
    }

    fun anthropicChat(model: String, prompt: String, token: String?): String {
        if (token.isNullOrBlank()) return "[Claude] Token missing. Enter it in Settings."
        return try {
            val url = URL("https://api.anthropic.com/v1/messages")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("x-api-key", token)
                setRequestProperty("anthropic-version", "2023-06-01")
                setRequestProperty("Content-Type", "application/json")
                doOutput = true
                connectTimeout = 20000; readTimeout = 20000
            }
            val body = JSONObject()
                .put("model", model)
                .put("max_tokens", 256)
                .put("messages", JSONArray().put(JSONObject()
                    .put("role","user")
                    .put("content", JSONArray().put(JSONObject().put("type","text").put("text", prompt)))
                ))
            conn.outputStream.use { it.write(body.toString().toByteArray(Charsets.UTF_8)) }
            val code = conn.responseCode
            val text = (if (code in 200..299) conn.inputStream else conn.errorStream)
                .bufferedReader().use(BufferedReader::readText)
            if (code !in 200..299) return "[Claude] HTTP $code: $text"
            val root = JSONObject(text)
            val content = root.optJSONArray("content")
            val first = content?.optJSONObject(0)
            first?.optString("text") ?: "[Claude] No content."
        } catch (t: Throwable) { "[Claude] Error: ${t.message}" }
    }

    fun geminiChat(model: String, prompt: String, token: String?): String {
        if (token.isNullOrBlank()) return "[Gemini] Token missing. Enter it in Settings."
        return try {
            val url = URL("https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$token")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                doOutput = true
                connectTimeout = 20000; readTimeout = 20000
            }
            val body = JSONObject()
                .put("contents", JSONArray().put(JSONObject()
                    .put("parts", JSONArray().put(JSONObject().put("text", prompt)))
                ))
            conn.outputStream.use { it.write(body.toString().toByteArray(Charsets.UTF_8)) }
            val code = conn.responseCode
            val text = (if (code in 200..299) conn.inputStream else conn.errorStream)
                .bufferedReader().use(BufferedReader::readText)
            if (code !in 200..299) return "[Gemini] HTTP $code: $text"
            val root = JSONObject(text)
            val out = root.optJSONArray("candidates")
                ?.optJSONObject(0)?.optJSONObject("content")
                ?.optJSONArray("parts")?.optJSONObject(0)?.optString("text")
            out ?: "[Gemini] No content."
        } catch (t: Throwable) { "[Gemini] Error: ${t.message}" }
    }

    fun grokChat(prompt: String): String {
        return "[Grok] API not yet available. Prompt='$prompt'"
    }
}
