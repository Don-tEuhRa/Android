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

public val MyIconPack.ThumbUpSolid: ImageVector
    get() {
        if (_thumbUpSolid != null) {
            return _thumbUpSolid!!
        }
        _thumbUpSolid = Builder(name = "ThumbUpSolid", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(7.493f, 18.75f)
                curveToRelative(-0.425f, 0.0f, -0.82f, -0.236f, -0.975f, -0.632f)
                arcTo(7.48f, 7.48f, 0.0f, false, true, 6.0f, 15.375f)
                curveToRelative(0.0f, -1.75f, 0.599f, -3.358f, 1.602f, -4.634f)
                curveToRelative(0.151f, -0.192f, 0.373f, -0.309f, 0.6f, -0.397f)
                curveToRelative(0.473f, -0.183f, 0.89f, -0.514f, 1.212f, -0.924f)
                arcToRelative(9.042f, 9.042f, 0.0f, false, true, 2.861f, -2.4f)
                curveToRelative(0.723f, -0.384f, 1.35f, -0.956f, 1.653f, -1.715f)
                arcToRelative(4.498f, 4.498f, 0.0f, false, false, 0.322f, -1.672f)
                verticalLineTo(3.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.75f, -0.75f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, true, 2.25f, 2.25f)
                curveToRelative(0.0f, 1.152f, -0.26f, 2.243f, -0.723f, 3.218f)
                curveToRelative(-0.266f, 0.558f, 0.107f, 1.282f, 0.725f, 1.282f)
                horizontalLineToRelative(3.126f)
                curveToRelative(1.026f, 0.0f, 1.945f, 0.694f, 2.054f, 1.715f)
                curveToRelative(0.045f, 0.422f, 0.068f, 0.85f, 0.068f, 1.285f)
                arcToRelative(11.95f, 11.95f, 0.0f, false, true, -2.649f, 7.521f)
                curveToRelative(-0.388f, 0.482f, -0.987f, 0.729f, -1.605f, 0.729f)
                horizontalLineTo(14.23f)
                curveToRelative(-0.483f, 0.0f, -0.964f, -0.078f, -1.423f, -0.23f)
                lineToRelative(-3.114f, -1.04f)
                arcToRelative(4.501f, 4.501f, 0.0f, false, false, -1.423f, -0.23f)
                horizontalLineToRelative(-0.777f)
                close()
                moveTo(2.331f, 10.977f)
                arcToRelative(11.969f, 11.969f, 0.0f, false, false, -0.831f, 4.398f)
                arcToRelative(12.0f, 12.0f, 0.0f, false, false, 0.52f, 3.507f)
                curveToRelative(0.26f, 0.85f, 1.084f, 1.368f, 1.973f, 1.368f)
                horizontalLineTo(4.9f)
                curveToRelative(0.445f, 0.0f, 0.72f, -0.498f, 0.523f, -0.898f)
                arcToRelative(8.963f, 8.963f, 0.0f, false, true, -0.924f, -3.977f)
                curveToRelative(0.0f, -1.708f, 0.476f, -3.305f, 1.302f, -4.666f)
                curveToRelative(0.245f, -0.403f, -0.028f, -0.959f, -0.5f, -0.959f)
                horizontalLineTo(4.25f)
                curveToRelative(-0.832f, 0.0f, -1.612f, 0.453f, -1.918f, 1.227f)
                close()
            }
        }
        .build()
        return _thumbUpSolid!!
    }

private var _thumbUpSolid: ImageVector? = null
