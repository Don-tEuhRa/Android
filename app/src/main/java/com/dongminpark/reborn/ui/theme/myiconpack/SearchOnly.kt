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

public val MyIconPack.SearchOnly: ImageVector
    get() {
        if (_searchOnly != null) {
            return _searchOnly!!
        }
        _searchOnly = Builder(name = "SearchOnly", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(21.0f, 21.0f)
                lineToRelative(-5.197f, -5.197f)
                moveToRelative(0.0f, 0.0f)
                arcTo(7.5f, 7.5f, 0.0f, true, false, 5.196f, 5.196f)
                arcToRelative(7.5f, 7.5f, 0.0f, false, false, 10.607f, 10.607f)
                close()
            }
        }
        .build()
        return _searchOnly!!
    }

private var _searchOnly: ImageVector? = null
