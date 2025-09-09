
package com.example.aiconsciousness.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.store by preferencesDataStore("settings")
object Keys { val Remember = booleanPreferencesKey("remember"); val Model = stringPreferencesKey("model") }
class AppState(ctx: Context) {
  val remember: Flow<Boolean> = ctx.store.data.map { it[Keys.Remember] ?: true }
  val model: Flow<String> = ctx.store.data.map { it[Keys.Model] ?: "gpt-4o-mini" }
}
