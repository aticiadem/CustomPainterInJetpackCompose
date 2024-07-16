package com.adematici.custompainter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter

class CrossCirclePainter : Painter() {
    override val intrinsicSize: Size = Size(100f, 100f)

    override fun DrawScope.onDraw() {
        // Circle
        drawCircle(
            color = Color.Blue,
            radius = size.minDimension / 2,
            center = center,
        )

        // Cross
        val strokeWidth = 5f

        drawLine(
            color = Color.Red,
            start = Offset(center.x - size.minDimension / 4, center.y - size.minDimension / 4),
            end = Offset(center.x + size.minDimension / 4, center.y + size.minDimension / 4),
            strokeWidth = strokeWidth,
        )

        drawLine(
            color = Color.Red,
            start = Offset(center.x - size.minDimension / 4, center.y + size.minDimension / 4),
            end = Offset(center.x + size.minDimension / 4, center.y - size.minDimension / 4),
            strokeWidth = strokeWidth,
        )
    }
}