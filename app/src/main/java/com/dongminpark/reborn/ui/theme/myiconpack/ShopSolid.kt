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

public val MyIconPack.ShopSolid: ImageVector
    get() {
        if (_shopSolid != null) {
            return _shopSolid!!
        }
        _shopSolid = Builder(name = "ShopSolid", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(7.5f, 6.0f)
                verticalLineToRelative(0.75f)
                lineTo(5.513f, 6.75f)
                curveToRelative(-0.96f, 0.0f, -1.764f, 0.724f, -1.865f, 1.679f)
                lineToRelative(-1.263f, 12.0f)
                arcTo(1.875f, 1.875f, 0.0f, false, false, 4.25f, 22.5f)
                horizontalLineToRelative(15.5f)
                arcToRelative(1.875f, 1.875f, 0.0f, false, false, 1.865f, -2.071f)
                lineToRelative(-1.263f, -12.0f)
                arcToRelative(1.875f, 1.875f, 0.0f, false, false, -1.865f, -1.679f)
                lineTo(16.5f, 6.75f)
                lineTo(16.5f, 6.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, true, false, -9.0f, 0.0f)
                close()
                moveTo(12.0f, 3.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, -3.0f, 3.0f)
                verticalLineToRelative(0.75f)
                horizontalLineToRelative(6.0f)
                lineTo(15.0f, 6.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, -3.0f, -3.0f)
                close()
                moveTo(9.0f, 11.25f)
                arcToRelative(3.0f, 3.0f, 0.0f, true, false, 6.0f, 0.0f)
                verticalLineToRelative(-0.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.5f, 0.0f)
                verticalLineToRelative(0.75f)
                arcToRelative(4.5f, 4.5f, 0.0f, true, true, -9.0f, 0.0f)
                verticalLineToRelative(-0.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.5f, 0.0f)
                verticalLineToRelative(0.75f)
                close()
            }
        }
        .build()
        return _shopSolid!!
    }

private var _shopSolid: ImageVector? = null
