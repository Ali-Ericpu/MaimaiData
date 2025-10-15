package org.plantalpha.maimaidata

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.plantalpha.maimaidata.feature.main.MainGraph
import org.plantalpha.maimaidata.ui.theme.MaimaiDataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT > 28) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            MaimaiDataTheme {
                MainGraph()
            }
        }
    }
}
