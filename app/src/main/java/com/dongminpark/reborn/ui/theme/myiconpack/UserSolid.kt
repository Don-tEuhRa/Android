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

public val MyIconPack.UserSolid: ImageVector
    get() {
        if (_userSolid != null) {
            return _userSolid!!
        }
        _userSolid = Builder(name = "UserSolid", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(7.5f, 6.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, true, true, 9.0f, 0.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, false, true, -9.0f, 0.0f)
                close()
                moveTo(3.751f, 20.105f)
                arcToRelative(8.25f, 8.25f, 0.0f, false, true, 16.498f, 0.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.437f, 0.695f)
                arcTo(18.683f, 18.683f, 0.0f, false, true, 12.0f, 22.5f)
                curveToRelative(-2.786f, 0.0f, -5.433f, -0.608f, -7.812f, -1.7f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.437f, -0.695f)
                close()
            }
        }
        .build()
        return _userSolid!!
    }

private var _userSolid: ImageVector? = null
