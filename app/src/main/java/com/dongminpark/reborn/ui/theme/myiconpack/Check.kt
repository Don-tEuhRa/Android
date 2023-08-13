package com.dongminpark.reborn.ui.theme.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.dongminpark.reborn.ui.theme.MyIconPack

public val MyIconPack.Check: ImageVector
    get() {
        if (_check != null) {
            return _check!!
        }
        _check = Builder(name = "Check", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(19.916f, 4.626f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.208f, 1.04f)
                lineToRelative(-9.0f, 13.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -1.154f, 0.114f)
                lineToRelative(-6.0f, -6.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.06f, -1.06f)
                lineToRelative(5.353f, 5.353f)
                lineToRelative(8.493f, -12.739f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.04f, -0.208f)
                close()
            }
        }
        .build()
        return _check!!
    }

private var _check: ImageVector? = null
