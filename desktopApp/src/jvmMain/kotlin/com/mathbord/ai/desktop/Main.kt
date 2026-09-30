package com.mathbord.ai.desktop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mathbord.ai.shared.PlatformKind
import com.mathbord.ai.shared.currentPlatform

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MathBord AI"
    ) {
        MaterialTheme {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("MathBord AI")
                Text(
                    when (currentPlatform()) {
                        PlatformKind.DESKTOP -> "Windows/Desktop foundation active"
                        PlatformKind.ANDROID -> "Android"
                    }
                )
            }
        }
    }
}
