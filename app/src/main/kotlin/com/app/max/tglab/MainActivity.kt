package com.app.max.tglab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.max.tglab.core.designsystem.theme.TgLabTheme
import com.app.max.tglab.navigation.TgLabNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TgLabTheme {
                TgLabNavHost()
            }
        }
    }
}
