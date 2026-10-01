package com.mathbord.ai.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mathbord.ai.shared.BoardDocument
import com.mathbord.ai.shared.BoardEngine
import com.mathbord.ai.shared.EntityId
import com.mathbord.ai.shared.InkTool
import com.mathbord.ai.shared.InputSource
import com.mathbord.ai.shared.StrokePoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class BoardUiState(
    val document: BoardDocument,
    val selectedTool: InkTool = InkTool.PEN,
    val canUndo: Boolean = false,
    val canRedo: Boolean = false
)

class BoardViewModel : ViewModel() {

    private val engine = BoardEngine(
        BoardDocument(
            id = EntityId("active-board"),
            width = 1f,
            height = 1f
        )
    )

    private val _uiState = MutableStateFlow(
        BoardUiState(document = engine.snapshot)
    )
    val uiState = _uiState.asStateFlow()

    fun setTool(tool: InkTool) {
        _uiState.update { it.copy(selectedTool = tool) }
    }

    fun resize(width: Float, height: Float) {
        engine.resize(width, height)
        publish()
    }

    fun beginStroke(source: InputSource, point: StrokePoint) {
        engine.beginStroke(source, _uiState.value.selectedTool, point)
    }

    fun appendStrokePoint(point: StrokePoint) {
        engine.appendStrokePoint(point)
    }

    fun endStroke() {
        engine.endStroke()
        publish()
    }

    fun beginErase() {
        engine.beginErase()
    }

    fun eraseAt(point: StrokePoint, radius: Float) {
        engine.eraseAt(point, radius)
        publish()
    }

    fun endErase() {
        engine.endErase()
        publish()
    }

    fun undo() {
        if (engine.undo()) publish()
    }

    fun redo() {
        if (engine.redo()) publish()
    }

    fun clear() {
        if (engine.clear()) publish()
    }

    private fun publish() {
        _uiState.value = _uiState.value.copy(
            document = engine.snapshot,
            canUndo = engine.canUndo,
            canRedo = engine.canRedo
        )
    }
}
