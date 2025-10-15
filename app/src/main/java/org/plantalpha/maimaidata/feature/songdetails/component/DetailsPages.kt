package org.plantalpha.maimaidata.feature.songdetails.component

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.plantalpha.maimaidata.domain.model.Song
import kotlin.math.abs

@Preview
@Composable
fun DetailsPages(
    song: Song = Song.song,
    tabs: List<String> = listOf("MAS", "EXP", "ADV", "BAS"),
) {
    val pagerState = rememberPagerState { song.charts.size }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            modifier = Modifier
                .fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            indicator = { PagerTabIndicator(it, pagerState) }
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = { Text(tab) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }
        HorizontalPager(pagerState, modifier = Modifier.fillMaxSize()) { index ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                item {
                    val color = MaterialTheme.colorScheme.onBackground
                    LabelToText("等级", "12.9", color)
                    LabelToText("拟合定数", "-", color)
                    LabelToText("铺面作者", "打卡为代表的帮我", color)
                    LabelToText("达成状态", "暂无成绩", color)
                }
                val maxValue = mapOf(
                    "Total" to 1000,
                    "Tab" to 1000,
                    "Hold" to 1000,
                    "Slide" to 1000,
                    "Torch" to 1000,
                    "Break" to 1000,
                )
                items(song.charts[pagerState.currentPage].notes.entries) { (label, value) ->
                    ScoreSlider(
                        label = label,
                        score = value,
                        maxScore = maxValue[label]!!,
                        color = song.basicInfo.genre.theme
                    )
                }
            }
        }

    }
}

@Composable
fun PagerTabIndicator(
    tabPositions: List<TabPosition>,
    pagerState: PagerState,
    color: Color = Color.DarkGray,
    @FloatRange(from = 0.0, to = 1.0) percent: Float = 0.3f,
    height: Dp = 5.dp,
) {
    val currentPage by rememberUpdatedState(newValue = pagerState.currentPage)
    val fraction by rememberUpdatedState(newValue = pagerState.currentPageOffsetFraction)
    val currentTab = tabPositions[currentPage]
    val previousTab = tabPositions.getOrNull(currentPage - 1)
    val nextTab = tabPositions.getOrNull(currentPage + 1)
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val indicatorWidth = currentTab.width.toPx() * percent
            val indicatorOffset = if (fraction > 0 && nextTab != null) {
                lerp(currentTab.left, nextTab.left, fraction).toPx()
            } else if (fraction < 0 && previousTab != null) {
                lerp(currentTab.left, previousTab.left, -fraction).toPx()
            } else {
                currentTab.left.toPx()
            }

            val canvasHeight = size.height
            drawRoundRect(
                color = color,
                topLeft = Offset(
                    indicatorOffset + (currentTab.width.toPx() * (1 - percent) / 2),
                    canvasHeight - height.toPx()
                ),
                size = Size(indicatorWidth + indicatorWidth * abs(fraction), height.toPx()),
                cornerRadius = CornerRadius(50f)
            )
        }
    )
}

@Preview
@Composable
fun ScoreSlider(
    label: String = "Label",
    score: Int = 880,
    maxScore: Int = 1000,
    color: Color = Color.Green
) {
    val textColor = MaterialTheme.colorScheme.onBackground
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(2.dp)
            .height(28.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            textAlign = TextAlign.End,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .weight(7f)
                .fillMaxSize()
                .padding(vertical = 4.dp, horizontal = 12.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(score / maxScore.toFloat())
                    .background(color, RoundedCornerShape(8.dp))
            )
        }
        Text(
            text = score.toString(),
            color = textColor,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
    }
}