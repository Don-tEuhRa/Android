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

public val MyIconPack.SearchOutline: ImageVector
    get() {
        if (_searchOutline != null) {
            return _searchOutline!!
        }
        _searchOutline = Builder(name = "SearchOutline", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(15.75f, 15.75f)
                lineToRelative(-2.489f, -2.489f)
                moveToRelative(0.0f, 0.0f)
                arcToRelative(3.375f, 3.375f, 0.0f, true, false, -4.773f, -4.773f)
                arcToRelative(3.375f, 3.375f, 0.0f, false, false, 4.774f, 4.774f)
                close()
                moveTo(21.0f, 12.0f)
                arcToRelative(9.0f, 9.0f, 0.0f, true, true, -18.0f, 0.0f)
                arcToRelative(9.0f, 9.0f, 0.0f, false, true, 18.0f, 0.0f)
                close()
            }
        }
        .build()
        return _searchOutline!!
    }

private var _searchOutline: ImageVector? = null
