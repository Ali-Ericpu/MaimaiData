package org.plantalpha.maimaidata.feature.songdetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.plantalpha.maimaidata.R
import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.extension.doubleBorder
import org.plantalpha.maimaidata.ui.theme.BorderColor
import org.plantalpha.maimaidata.ui.theme.MaimaiBlue
import org.plantalpha.maimaidata.ui.theme.MaimaiGoodGreen
import org.plantalpha.maimaidata.ui.theme.MaimaiGreatPink
import org.plantalpha.maimaidata.ui.theme.MaimaiMissGrey
import java.math.RoundingMode
import java.text.DecimalFormat

@Composable
fun DetailsScoreTable(chart: Song.Chart, color: Color, modifier: Modifier = Modifier) {
    val totalScore = with(chart.notes) { tap + (touch ?: 0) + hold * 2 + slide * 3 + `break` * 5 }
    val textColor = MaterialTheme.colorScheme.background
    Column(
        modifier = modifier.doubleBorder(color)
    ) {
        // 表头
        Row(modifier = Modifier.height(50.dp)) {
            LabelCell(text = stringResource(R.string.empty))
            LabelCell(
                text = stringResource(R.string.great),
                bgColor = MaimaiGreatPink,
                textColor = textColor
            )
            LabelCell(
                text = stringResource(R.string.good),
                bgColor = MaimaiGoodGreen,
                textColor = textColor
            )
            LabelCell(
                text = stringResource(R.string.miss),
                bgColor = MaimaiMissGrey,
                textColor = textColor
            )
        }
        val format = DecimalFormat("0.#####%").apply { roundingMode = RoundingMode.DOWN }
        // TAP 行
        ScoreRow(label = stringResource(R.string.tap), dividend = 1f, score = totalScore, format)

        // HOLD 行
        ScoreRow(label = stringResource(R.string.hold), dividend = 2f, score = totalScore, format)

        // SLIDE 行
        ScoreRow(label = stringResource(R.string.slide), dividend = 3f, score = totalScore, format)

        // BREAK 行 (特殊处理多行数值)
        Row(modifier = Modifier.height(150.dp)) {
            LabelCell(
                text = stringResource(R.string._break),
                bgColor = MaimaiBlue,
                textColor = textColor
            )

            // GREAT 列包含三个数值
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(0.5.dp, BorderColor)
            ) {
                ScoreCell(
                    text = format.format(5f / totalScore * 0.2 + (0.01 / chart.notes.`break`) * 0.6),
                    textColor = MaimaiGreatPink
                )
                ScoreCell(
                    text = format.format(5f / totalScore * 0.4 + (0.01 / chart.notes.`break`) * 0.6),
                    textColor = MaimaiGreatPink
                )
                ScoreCell(
                    text = format.format(5f / totalScore * 0.5 + (0.01 / chart.notes.`break`) * 0.6),
                    textColor = MaimaiGreatPink
                )
            }

            // GOOD 列
            ScoreCell(
                text = format.format(5f / totalScore * 0.6 + (0.01 / chart.notes.`break`) * 0.7),
                textColor = MaimaiGoodGreen
            )

            // MISS 列
            ScoreCell(
                text = format.format(5f / totalScore + 0.01 / chart.notes.`break`),
                textColor = MaimaiMissGrey
            )
        }

        // 最后一行 50落 / 100落
        Row(modifier = Modifier.height(50.dp)) {
            LabelCell(
                text = stringResource(R.string.break_50),
                bgColor = MaimaiBlue,
                textColor = textColor
            )
            ScoreCell(
                text = format.format(0.01 / chart.notes.`break` * 0.25),
                textColor = MaimaiMissGrey
            )
            LabelCell(
                text = stringResource(R.string.break_100),
                bgColor = MaimaiBlue,
                textColor = textColor
            )
            ScoreCell(
                text = format.format((0.01 / chart.notes.`break`) * 0.5),
                textColor = MaimaiMissGrey
            )
        }
    }
}

@Composable
fun ScoreRow(label: String, dividend: Float, score: Int, format: DecimalFormat) {
    Row(modifier = Modifier.height(50.dp)) {
        LabelCell(
            text = label,
            bgColor = MaimaiBlue,
            textColor = MaterialTheme.colorScheme.background
        )
        ScoreCell(text = format.format(dividend / score * 0.2), textColor = MaimaiGreatPink)
        ScoreCell(text = format.format(dividend / score * 0.5), textColor = MaimaiGoodGreen)
        ScoreCell(text = format.format(dividend / score), textColor = MaimaiMissGrey)
    }
}

@Composable
private fun RowScope.LabelCell(
    text: String,
    weight: Float = 1f,
    bgColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.background,
) {
    Box(
        modifier = Modifier
            .weight(weight)
            .fillMaxHeight()
            .background(bgColor)
            .border(0.5.dp, BorderColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Composable
private fun RowScope.ScoreCell(
    text: String,
    weight: Float = 1f,
    textColor: Color = MaterialTheme.colorScheme.background,
) {
    Box(
        modifier = Modifier
            .weight(weight)
            .fillMaxHeight()
            .border(0.5.dp, BorderColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Composable
private fun ColumnScope.ScoreCell(
    text: String,
    weight: Float = 1f,
    textColor: Color = MaterialTheme.colorScheme.background,
) {
    Box(
        modifier = Modifier
            .weight(weight)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(2.dp)
        )
    }
}

