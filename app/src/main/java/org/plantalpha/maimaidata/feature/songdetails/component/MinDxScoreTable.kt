package org.plantalpha.maimaidata.feature.songdetails.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import org.plantalpha.maimaidata.R
import org.plantalpha.maimaidata.domain.model.SongDxRank
import org.plantalpha.maimaidata.extension.doubleBorder
import org.plantalpha.maimaidata.ui.theme.MaimaiDataTheme
import kotlin.math.ceil

@Composable
fun MinDxScoreTable(
    maxDxScore: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .doubleBorder(color),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.min_dx_score),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        SongDxRank.entries.fastForEachIndexed { index, rank ->
            if (index == 0) return@fastForEachIndexed
            MinDxScoreRow(
                rank = index,
                score = ceil(maxDxScore * rank.achieve).toInt()
            )
            if (index < 5) {
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
            }
        }
    }
}

@Composable
private fun MinDxScoreRow(
    rank: Int,
    score: Int,
    modifier: Modifier = Modifier
) {
    val starColor = when (rank) {
        1, 2 -> Color(0xFF76D200) // Green
        3, 4 -> Color(0xFFFF5722) // Orange/Red
        5 -> Color(0xFFFFC107) // Yellow/Gold
        else -> Color.Gray
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(40.dp)
        ) {
            FourPointedStar(color = starColor, modifier = Modifier.size(36.dp))
            Text(
                text = rank.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        
        Text(
            text = score.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun FourPointedStar(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val outerRadius = size.width / 2
            val innerRadius = size.width / 4

            moveTo(centerX, 0f) // Top
            quadraticTo(centerX + innerRadius / 2, centerY - innerRadius / 2, centerX + outerRadius, centerY) // Right
            quadraticTo(centerX + innerRadius / 2, centerY + innerRadius / 2, centerX, centerY + outerRadius) // Bottom
            quadraticTo(centerX - innerRadius / 2, centerY + innerRadius / 2, 0f, centerY) // Left
            quadraticTo(centerX - innerRadius / 2, centerY - innerRadius / 2, centerX, 0f) // Back to Top
            close()
        }
        
        drawPath(
            path = path,
            color = color,
            style = Fill
        )
        drawPath(
            path = path,
            color = Color.White.copy(alpha = 0.5f),
            style = Stroke(width = 2.dp.toPx())
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MinDxScoreTablePreview() {
    MaimaiDataTheme {
        MinDxScoreTable(
            maxDxScore = 1000,
            color = Color.Blue,
        )
    }
}
