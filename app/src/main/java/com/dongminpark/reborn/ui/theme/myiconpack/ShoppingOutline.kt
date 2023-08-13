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

public val MyIconPack.ShoppingOutline: ImageVector
    get() {
        if (_shoppingOutline != null) {
            return _shoppingOutline!!
        }
        _shoppingOutline = Builder(name = "ShoppingOutline", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(2.25f, 3.0f)
                horizontalLineToRelative(1.386f)
                curveToRelative(0.51f, 0.0f, 0.955f, 0.343f, 1.087f, 0.835f)
                lineToRelative(0.383f, 1.437f)
                moveTo(7.5f, 14.25f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, -3.0f, 3.0f)
                horizontalLineToRelative(15.75f)
                moveToRelative(-12.75f, -3.0f)
                horizontalLineToRelative(11.218f)
                curveToRelative(1.121f, -2.3f, 2.1f, -4.684f, 2.924f, -7.138f)
                arcToRelative(60.114f, 60.114f, 0.0f, false, false, -16.536f, -1.84f)
                moveTo(7.5f, 14.25f)
                lineTo(5.106f, 5.272f)
                moveTo(6.0f, 20.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, -1.5f, 0.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.5f, 0.0f)
                close()
                moveTo(18.75f, 20.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, -1.5f, 0.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.5f, 0.0f)
                close()
            }
        }
        .build()
        return _shoppingOutline!!
    }

private var _shoppingOutline: ImageVector? = null
