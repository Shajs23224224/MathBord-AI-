package com.mathbord.ai.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Highlight
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mathbord.ai.shared.InkStroke
import com.mathbord.ai.shared.InkTool
import com.mathbord.ai.shared.InputSource
import com.mathbord.ai.shared.StrokePoint
import com.mathbord.ai.ui.viewmodel.BoardViewModel

@Composable
fun BoardScreen(
    viewModel: BoardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val tool = uiState.selectedTool
    val boardColorScheme = MaterialTheme.colorScheme

    Column(modifier = Modifier.fillMaxSize()) {
        BoardToolbar(
            selectedTool = tool,
            strokeCount = uiState.document.strokes.size,
            canUndo = uiState.canUndo,
            canRedo = uiState.canRedo,
            onToolSelected = viewModel::setTool,
            onUndo = viewModel::undo,
            onRedo = viewModel::redo,
            onClear = viewModel::clear
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(12.dp),
            tonalElevation = 1.dp,
            shape = MaterialTheme.shapes.large
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .onSizeChanged {
                            viewModel.resize(it.width.toFloat(), it.height.toFloat())
                        }
                        .background(MaterialTheme.colorScheme.surface)
                        .pointerInput(tool) {
                            var strokeStarted = false

                            detectDragGestures(
                                onDragStart = {
                                    strokeStarted = false
                                },
                                onDragCancel = {
                                    if (strokeStarted) {
                                        if (tool == InkTool.ERASER) {
                                            viewModel.endErase()
                                        } else {
                                            viewModel.endStroke()
                                        }
                                    }
                                    strokeStarted = false
                                },
                                onDragEnd = {
                                    if (strokeStarted) {
                                        if (tool == InkTool.ERASER) {
                                            viewModel.endErase()
                                        } else {
                                            viewModel.endStroke()
                                        }
                                    }
                                    strokeStarted = false
                                },
                                onDrag = { change, _ ->
                                    val source = when (change.type) {
                                        PointerType.Stylus -> InputSource.STYLUS
                                        PointerType.Mouse -> InputSource.MOUSE
                                        PointerType.Touch -> InputSource.TOUCH
                                        else -> InputSource.OTHER
                                    }

                                    val point = StrokePoint(
                                        x = change.position.x,
                                        y = change.position.y,
                                        pressure = change.pressure.coerceIn(0.1f, 1f)
                                    )

                                    if (!strokeStarted) {
                                        when (tool) {
                                            InkTool.PEN,
                                            InkTool.HIGHLIGHTER -> {
                                                viewModel.beginStroke(source, point)
                                            }
                                            InkTool.ERASER -> viewModel.beginErase()
                                            InkTool.SELECT -> Unit
                                        }
                                        strokeStarted = true
                                    } else {
                                        when (tool) {
                                            InkTool.PEN,
                                            InkTool.HIGHLIGHTER -> viewModel.appendStrokePoint(point)
                                            InkTool.ERASER -> viewModel.eraseAt(point, radius = 24f)
                                            InkTool.SELECT -> Unit
                                        }
                                    }
                                    change.consume()
                                }
                            )
                        }
                ) {
                    val penColor = boardColorScheme.onSurface
                    val highlighterColor = boardColorScheme.primary

                    uiState.document.strokes.forEach { stroke ->
                        drawStroke(
                            stroke = stroke,
                            penColor = penColor,
                            highlighterColor = highlighterColor
                        )
                    }
                }

                if (uiState.document.strokes.isEmpty()) {
                    Text(
                        text = "Escribe una operación matemática aquí",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun BoardToolbar(
    selectedTool: InkTool,
    strokeCount: Int,
    canUndo: Boolean,
    canRedo: Boolean,
    onToolSelected: (InkTool) -> Unit,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onClear: () -> Unit
) {
    Surface(tonalElevation = 2.dp) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ToolChip(
                    label = "Lápiz",
                    selected = selectedTool == InkTool.PEN,
                    icon = Icons.Default.Create,
                    onClick = { onToolSelected(InkTool.PEN) }
                )
                ToolChip(
                    label = "Resaltador",
                    selected = selectedTool == InkTool.HIGHLIGHTER,
                    icon = Icons.Default.Highlight,
                    onClick = { onToolSelected(InkTool.HIGHLIGHTER) }
                )
                ToolChip(
                    label = "Borrador",
                    selected = selectedTool == InkTool.ERASER,
                    icon = Icons.Default.Delete,
                    onClick = { onToolSelected(InkTool.ERASER) }
                )

                IconButton(onClick = onUndo, enabled = canUndo) {
                    Icon(Icons.Default.Undo, contentDescription = "Deshacer")
                }
                IconButton(onClick = onRedo, enabled = canRedo) {
                    Icon(Icons.Default.Redo, contentDescription = "Rehacer")
                }
                IconButton(onClick = onClear, enabled = canUndo) {
                    Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                }
            }

            Text(
                text = "Trazos: ${strokeCount}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ToolChip(
    label: String,
    selected: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    )
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawStroke(
    stroke: InkStroke,
    penColor: Color,
    highlighterColor: Color
) {
    if (stroke.points.isEmpty()) return

    val path = Path().apply {
        val first = stroke.points.first()
        moveTo(first.x, first.y)
        stroke.points.drop(1).forEach { point ->
            lineTo(point.x, point.y)
        }
    }

    val paintColor = when (stroke.tool) {
        InkTool.PEN -> penColor
        InkTool.HIGHLIGHTER -> highlighterColor
        InkTool.ERASER,
        InkTool.SELECT -> Color.Transparent
    }

    val width = when (stroke.tool) {
        InkTool.PEN -> 3.5f
        InkTool.HIGHLIGHTER -> 18f
        InkTool.ERASER,
        InkTool.SELECT -> 1f
    }

    drawPath(
        path = path,
        color = paintColor,
        alpha = if (stroke.tool == InkTool.HIGHLIGHTER) 0.35f else 1f,
        style = androidx.compose.ui.graphics.drawscope.Stroke(
            width = width,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}
