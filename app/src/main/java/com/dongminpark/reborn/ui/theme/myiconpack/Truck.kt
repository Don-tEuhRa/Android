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

public val MyIconPack.Truck: ImageVector
    get() {
        if (_truck != null) {
            return _truck!!
        }
        _truck = Builder(name = "Truck", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(8.25f, 18.75f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, true, -3.0f, 0.0f)
                moveToRelative(3.0f, 0.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, false, -3.0f, 0.0f)
                moveToRelative(3.0f, 0.0f)
                horizontalLineToRelative(6.0f)
                moveToRelative(-9.0f, 0.0f)
                horizontalLineTo(3.375f)
                arcToRelative(1.125f, 1.125f, 0.0f, false, true, -1.125f, -1.125f)
                verticalLineTo(14.25f)
                moveToRelative(17.25f, 4.5f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, true, -3.0f, 0.0f)
                moveToRelative(3.0f, 0.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, false, -3.0f, 0.0f)
                moveToRelative(3.0f, 0.0f)
                horizontalLineToRelative(1.125f)
                curveToRelative(0.621f, 0.0f, 1.129f, -0.504f, 1.09f, -1.124f)
                arcToRelative(17.902f, 17.902f, 0.0f, false, false, -3.213f, -9.193f)
                arcToRelative(2.056f, 2.056f, 0.0f, false, false, -1.58f, -0.86f)
                horizontalLineTo(14.25f)
                moveTo(16.5f, 18.75f)
                horizontalLineToRelative(-2.25f)
                moveToRelative(0.0f, -11.177f)
                verticalLineToRelative(-0.958f)
                curveToRelative(0.0f, -0.568f, -0.422f, -1.048f, -0.987f, -1.106f)
                arcToRelative(48.554f, 48.554f, 0.0f, false, false, -10.026f, 0.0f)
                arcToRelative(1.106f, 1.106f, 0.0f, false, false, -0.987f, 1.106f)
                verticalLineToRelative(7.635f)
                moveToRelative(12.0f, -6.677f)
                verticalLineToRelative(6.677f)
                moveToRelative(0.0f, 4.5f)
                verticalLineToRelative(-4.5f)
                moveToRelative(0.0f, 0.0f)
                horizontalLineToRelative(-12.0f)
            }
        }
        .build()
        return _truck!!
    }

private var _truck: ImageVector? = null
