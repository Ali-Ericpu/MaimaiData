package org.plantalpha.maimaidata.extension

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.doubleBorder(color: Color) =
    this
        .padding(vertical = 4.dp)
        .border(
            width = 2.dp,
            color = color,
            shape = RoundedCornerShape(12.dp)
        )
        .padding(4.dp)
        .border(
            width = 1.dp,
            color = color,
            shape = RoundedCornerShape(8.dp)
        )
        .padding(4.dp)
