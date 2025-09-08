
package com.example.aiconsciousness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.aiconsciousness.ui.theme.AppTheme
import com.example.aiconsciousness.nav.AppNavHost
import com.example.aiconsciousness.data.AppState

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppTheme {
        val appState = remember { AppState(applicationContext) }
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          AppNavHost(appState = appState)
        }
      }
    }
  }
}
