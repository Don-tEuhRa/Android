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

public val MyIconPack.CheckcircleSolid: ImageVector
    get() {
        if (_checkcircleSolid != null) {
            return _checkcircleSolid!!
        }
        _checkcircleSolid = Builder(name = "CheckcircleSolid", defaultWidth = 24.0.dp, defaultHeight
                = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(2.25f, 12.0f)
                curveToRelative(0.0f, -5.385f, 4.365f, -9.75f, 9.75f, -9.75f)
                reflectiveCurveToRelative(9.75f, 4.365f, 9.75f, 9.75f)
                reflectiveCurveToRelative(-4.365f, 9.75f, -9.75f, 9.75f)
                reflectiveCurveTo(2.25f, 17.385f, 2.25f, 12.0f)
                close()
                moveTo(15.61f, 10.186f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, -1.22f, -0.872f)
                lineToRelative(-3.236f, 4.53f)
                lineTo(9.53f, 12.22f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.06f, 1.06f)
                lineToRelative(2.25f, 2.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.14f, -0.094f)
                lineToRelative(3.75f, -5.25f)
                close()
            }
        }
        .build()
        return _checkcircleSolid!!
    }

private var _checkcircleSolid: ImageVector? = null
