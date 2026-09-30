package com.mathbord.ai.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.mathbord.ai.core.ui.MathBordSpacing

@Composable
fun PlaceholderScreen(
    title: String,
    description: String,
    interactionCount: Int = 0,
    onInteraction: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    MathBordContentSurface(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MathBordSpacing.lg)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.semantics { heading() }
            )
            Text(text = description, style = MaterialTheme.typography.bodyLarge)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(MathBordSpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(MathBordSpacing.md)
                ) {
                    Text(
                        text = "Infraestructura de Fase 1",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Estado preservable: $interactionCount",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Button(onClick = onInteraction) {
                        Text("Probar estado y evento")
                    }
                }
            }
            Text(
                text = "El contenido de esta sección se implementará en su fase funcional.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = MathBordSpacing.sm)
            )
        }
    }
}
