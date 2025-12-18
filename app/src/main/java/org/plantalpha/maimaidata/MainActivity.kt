package org.plantalpha.maimaidata

import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import org.plantalpha.maimaidata.navigation.NavigationRoot
import org.plantalpha.maimaidata.ui.theme.MaimaiDataTheme

@HiltAndroidApp
class MaiMaiData : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT > 28) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            MaimaiDataTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    NavigationRoot()
}
