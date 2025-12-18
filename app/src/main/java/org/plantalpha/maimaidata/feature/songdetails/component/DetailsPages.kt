package org.plantalpha.maimaidata.feature.songdetails.component

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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.plantalpha.maimaidata.domain.model.Song

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
        SecondaryTabRow(
            modifier = Modifier
                .fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        pagerState.currentPage,
                        matchContentSize = false
                    ),
                    color = song.basicInfo.genre.theme
                )
            }
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
                    "Touch" to 1000,
                    "Break" to 1000,
                )
                items(song.charts[pagerState.currentPage].notes.entries()) { (label, value) ->
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
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
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