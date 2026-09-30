package com.mathbord.ai.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mathbord.ai.core.ui.MathBordDimensions
import com.mathbord.ai.core.ui.MathBordSpacing

@Composable
fun MathBordContentSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = MathBordDimensions.contentMaxWidth)
            .padding(horizontal = MathBordSpacing.lg, vertical = MathBordSpacing.xl)
    ) {
        content()
    }
}
