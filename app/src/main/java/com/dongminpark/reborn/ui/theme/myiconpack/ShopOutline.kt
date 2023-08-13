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

public val MyIconPack.ShopOutline: ImageVector
    get() {
        if (_shopOutline != null) {
            return _shopOutline!!
        }
        _shopOutline = Builder(name = "ShopOutline", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(15.75f, 10.5f)
                lineTo(15.75f, 6.0f)
                arcToRelative(3.75f, 3.75f, 0.0f, true, false, -7.5f, 0.0f)
                verticalLineToRelative(4.5f)
                moveToRelative(11.356f, -1.993f)
                lineToRelative(1.263f, 12.0f)
                curveToRelative(0.07f, 0.665f, -0.45f, 1.243f, -1.119f, 1.243f)
                lineTo(4.25f, 21.75f)
                arcToRelative(1.125f, 1.125f, 0.0f, false, true, -1.12f, -1.243f)
                lineToRelative(1.264f, -12.0f)
                arcTo(1.125f, 1.125f, 0.0f, false, true, 5.513f, 7.5f)
                horizontalLineToRelative(12.974f)
                curveToRelative(0.576f, 0.0f, 1.059f, 0.435f, 1.119f, 1.007f)
                close()
                moveTo(8.625f, 10.5f)
                arcToRelative(0.375f, 0.375f, 0.0f, true, true, -0.75f, 0.0f)
                arcToRelative(0.375f, 0.375f, 0.0f, false, true, 0.75f, 0.0f)
                close()
                moveTo(16.125f, 10.5f)
                arcToRelative(0.375f, 0.375f, 0.0f, true, true, -0.75f, 0.0f)
                arcToRelative(0.375f, 0.375f, 0.0f, false, true, 0.75f, 0.0f)
                close()
            }
        }
        .build()
        return _shopOutline!!
    }

private var _shopOutline: ImageVector? = null
