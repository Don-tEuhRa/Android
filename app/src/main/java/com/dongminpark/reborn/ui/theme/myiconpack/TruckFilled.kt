package com.dongminpark.reborn.ui.theme.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.dongminpark.reborn.ui.theme.MyIconPack

public val MyIconPack.TruckFilled: ImageVector
    get() {
        if (_truckFilled != null) {
            return _truckFilled!!
        }
        _truckFilled = Builder(name = "TruckFilled", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(3.375f, 4.5f)
                curveTo(2.339f, 4.5f, 1.5f, 5.34f, 1.5f, 6.375f)
                verticalLineTo(13.5f)
                horizontalLineToRelative(12.0f)
                verticalLineTo(6.375f)
                curveToRelative(0.0f, -1.036f, -0.84f, -1.875f, -1.875f, -1.875f)
                horizontalLineToRelative(-8.25f)
                close()
                moveTo(13.5f, 15.0f)
                horizontalLineToRelative(-12.0f)
                verticalLineToRelative(2.625f)
                curveToRelative(0.0f, 1.035f, 0.84f, 1.875f, 1.875f, 1.875f)
                horizontalLineToRelative(0.375f)
                arcToRelative(3.0f, 3.0f, 0.0f, true, true, 6.0f, 0.0f)
                horizontalLineToRelative(3.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.75f, -0.75f)
                verticalLineTo(15.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.25f, 19.5f)
                arcToRelative(1.5f, 1.5f, 0.0f, true, false, -3.0f, 0.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, false, 3.0f, 0.0f)
                close()
                moveTo(15.75f, 6.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, 0.75f)
                verticalLineToRelative(11.25f)
                curveToRelative(0.0f, 0.087f, 0.015f, 0.17f, 0.042f, 0.248f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, 5.958f, 0.464f)
                curveToRelative(0.853f, -0.175f, 1.522f, -0.935f, 1.464f, -1.883f)
                arcToRelative(18.659f, 18.659f, 0.0f, false, false, -3.732f, -10.104f)
                arcToRelative(1.837f, 1.837f, 0.0f, false, false, -1.47f, -0.725f)
                horizontalLineTo(15.75f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(19.5f, 19.5f)
                arcToRelative(1.5f, 1.5f, 0.0f, true, false, -3.0f, 0.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, false, 3.0f, 0.0f)
                close()
            }
        }
        .build()
        return _truckFilled!!
    }

private var _truckFilled: ImageVector? = null
