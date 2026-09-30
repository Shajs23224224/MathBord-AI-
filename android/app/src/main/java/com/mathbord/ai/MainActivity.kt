package com.mathbord.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mathbord.ai.ui.MathBordApp
import com.mathbord.ai.ui.theme.MathBordTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MathBordTheme { MathBordApp() }
        }
    }
}
