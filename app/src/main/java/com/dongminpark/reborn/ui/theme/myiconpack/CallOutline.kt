package com.dongminpark.reborn.ui.theme.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.dongminpark.reborn.ui.theme.MyIconPack

public val MyIconPack.CallOutline: ImageVector
    get() {
        if (_callOutline != null) {
            return _callOutline!!
        }
        _callOutline = Builder(name = "CallOutline", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(2.25f, 6.75f)
                curveToRelative(0.0f, 8.284f, 6.716f, 15.0f, 15.0f, 15.0f)
                horizontalLineToRelative(2.25f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, false, 2.25f, -2.25f)
                verticalLineToRelative(-1.372f)
                curveToRelative(0.0f, -0.516f, -0.351f, -0.966f, -0.852f, -1.091f)
                lineToRelative(-4.423f, -1.106f)
                curveToRelative(-0.44f, -0.11f, -0.902f, 0.055f, -1.173f, 0.417f)
                lineToRelative(-0.97f, 1.293f)
                curveToRelative(-0.282f, 0.376f, -0.769f, 0.542f, -1.21f, 0.38f)
                arcToRelative(12.035f, 12.035f, 0.0f, false, true, -7.143f, -7.143f)
                curveToRelative(-0.162f, -0.441f, 0.004f, -0.928f, 0.38f, -1.21f)
                lineToRelative(1.293f, -0.97f)
                curveToRelative(0.363f, -0.271f, 0.527f, -0.734f, 0.417f, -1.173f)
                lineTo(6.963f, 3.102f)
                arcToRelative(1.125f, 1.125f, 0.0f, false, false, -1.091f, -0.852f)
                horizontalLineTo(4.5f)
                arcTo(2.25f, 2.25f, 0.0f, false, false, 2.25f, 4.5f)
                verticalLineToRelative(2.25f)
                close()
            }
        }
        .build()
        return _callOutline!!
    }

private var _callOutline: ImageVector? = null
