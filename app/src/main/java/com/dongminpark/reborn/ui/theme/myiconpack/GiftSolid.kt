package com.dongminpark.reborn.ui.theme.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.dongminpark.reborn.ui.theme.MyIconPack

public val MyIconPack.GiftSolid: ImageVector
    get() {
        if (_giftSolid != null) {
            return _giftSolid!!
        }
        _giftSolid = Builder(name = "GiftSolid", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.375f, 3.0f)
                arcToRelative(1.875f, 1.875f, 0.0f, false, false, 0.0f, 3.75f)
                horizontalLineToRelative(1.875f)
                verticalLineToRelative(4.5f)
                horizontalLineTo(3.375f)
                arcTo(1.875f, 1.875f, 0.0f, false, true, 1.5f, 9.375f)
                verticalLineToRelative(-0.75f)
                curveToRelative(0.0f, -1.036f, 0.84f, -1.875f, 1.875f, -1.875f)
                horizontalLineToRelative(3.193f)
                arcTo(3.375f, 3.375f, 0.0f, false, true, 12.0f, 2.753f)
                arcToRelative(3.375f, 3.375f, 0.0f, false, true, 5.432f, 3.997f)
                horizontalLineToRelative(3.943f)
                curveToRelative(1.035f, 0.0f, 1.875f, 0.84f, 1.875f, 1.875f)
                verticalLineToRelative(0.75f)
                curveToRelative(0.0f, 1.036f, -0.84f, 1.875f, -1.875f, 1.875f)
                horizontalLineTo(12.75f)
                verticalLineToRelative(-4.5f)
                horizontalLineToRelative(1.875f)
                arcToRelative(1.875f, 1.875f, 0.0f, true, false, -1.875f, -1.875f)
                verticalLineTo(6.75f)
                horizontalLineToRelative(-1.5f)
                verticalLineTo(4.875f)
                curveTo(11.25f, 3.839f, 10.41f, 3.0f, 9.375f, 3.0f)
                close()
                moveTo(11.25f, 12.75f)
                horizontalLineTo(3.0f)
                verticalLineToRelative(6.75f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, false, 2.25f, 2.25f)
                horizontalLineToRelative(6.0f)
                verticalLineToRelative(-9.0f)
                close()
                moveTo(12.75f, 12.75f)
                verticalLineToRelative(9.0f)
                horizontalLineToRelative(6.75f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, false, 2.25f, -2.25f)
                verticalLineToRelative(-6.75f)
                horizontalLineToRelative(-9.0f)
                close()
            }
        }
        .build()
        return _giftSolid!!
    }

private var _giftSolid: ImageVector? = null
