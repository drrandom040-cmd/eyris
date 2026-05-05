package com.elsewhere.eyris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.elsewhere.eyris.ui.EyrisApp
import com.elsewhere.eyris.ui.theme.EyrisTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Timber logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        setContent {
            EyrisTheme {
                Surface(
                    modifier = Modifier,
                    color = Color(0xFF1A1A2E)
                ) {
                    EyrisApp()
                }
            }
        }
    }
}
