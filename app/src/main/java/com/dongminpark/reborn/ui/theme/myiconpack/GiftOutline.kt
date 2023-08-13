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

public val MyIconPack.GiftOutline: ImageVector
    get() {
        if (_giftOutline != null) {
            return _giftOutline!!
        }
        _giftOutline = Builder(name = "GiftOutline", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(21.0f, 11.25f)
                verticalLineToRelative(8.25f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, true, -1.5f, 1.5f)
                horizontalLineTo(5.25f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, true, -1.5f, -1.5f)
                verticalLineToRelative(-8.25f)
                moveTo(12.0f, 4.875f)
                arcTo(2.625f, 2.625f, 0.0f, true, false, 9.375f, 7.5f)
                horizontalLineTo(12.0f)
                moveToRelative(0.0f, -2.625f)
                verticalLineTo(7.5f)
                moveToRelative(0.0f, -2.625f)
                arcTo(2.625f, 2.625f, 0.0f, true, true, 14.625f, 7.5f)
                horizontalLineTo(12.0f)
                moveToRelative(0.0f, 0.0f)
                verticalLineTo(21.0f)
                moveToRelative(-8.625f, -9.75f)
                horizontalLineToRelative(18.0f)
                curveToRelative(0.621f, 0.0f, 1.125f, -0.504f, 1.125f, -1.125f)
                verticalLineToRelative(-1.5f)
                curveToRelative(0.0f, -0.621f, -0.504f, -1.125f, -1.125f, -1.125f)
                horizontalLineToRelative(-18.0f)
                curveToRelative(-0.621f, 0.0f, -1.125f, 0.504f, -1.125f, 1.125f)
                verticalLineToRelative(1.5f)
                curveToRelative(0.0f, 0.621f, 0.504f, 1.125f, 1.125f, 1.125f)
                close()
            }
        }
        .build()
        return _giftOutline!!
    }

private var _giftOutline: ImageVector? = null
