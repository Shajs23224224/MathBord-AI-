package com.mathbord.ai.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mathbord.ai.core.ui.MathBordSpacing
import com.mathbord.ai.core.ui.UiStatus

@Composable
fun AsyncStateView(
    status: UiStatus,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    when (status) {
        UiStatus.Content -> content()
        UiStatus.Loading -> Column(
            modifier = modifier.fillMaxWidth().padding(MathBordSpacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MathBordSpacing.md)
        ) {
            CircularProgressIndicator()
            Text("Cargando…", style = MaterialTheme.typography.bodyLarge)
        }
        UiStatus.Empty -> Column(
            modifier = modifier.fillMaxWidth().padding(MathBordSpacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MathBordSpacing.sm)
        ) {
            Text("Sin contenido todavía.", style = MaterialTheme.typography.titleMedium)
            Text("Esta pantalla está preparada para recibir datos.", style = MaterialTheme.typography.bodyMedium)
        }
        is UiStatus.Error -> Column(
            modifier = modifier.fillMaxWidth().padding(MathBordSpacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MathBordSpacing.sm)
        ) {
            Text("No se pudo cargar el contenido.", style = MaterialTheme.typography.titleMedium)
            Text(status.message, style = MaterialTheme.typography.bodyMedium)
            if (onRetry != null) TextButton(onClick = onRetry) { Text("Reintentar") }
        }
    }
}
