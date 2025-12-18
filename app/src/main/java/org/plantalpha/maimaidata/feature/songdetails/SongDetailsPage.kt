package org.plantalpha.maimaidata.feature.songdetails

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.feature.songdetails.component.DetailsInfo
import org.plantalpha.maimaidata.feature.songdetails.component.DetailsPages
import org.plantalpha.maimaidata.feature.songdetails.component.DetailsTopBar
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@Preview
@Composable
fun SongDetailsPage(
    song: Song = Song.song,
    onBack : () -> Unit = {}
) {
    var maxOffsetY by remember { mutableIntStateOf(0) }
    val scrollState = remember { DetailsScrollState(0, 0) }
    val flingBehavior = ScrollableDefaults.flingBehavior()
    Scaffold(
        topBar = {
            DetailsTopBar(
                title = song.title,
                artist = song.basicInfo.artist,
                color = song.basicInfo.genre.theme,
                onBack = onBack,
            ) {
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .scrollable(scrollState, Orientation.Vertical)
                .nestedScroll(
                    remember {
                        object : NestedScrollConnection {
                            override fun onPreScroll(
                                available: Offset,
                                source: NestedScrollSource
                            ): Offset {
                                val availableY = available.y
                                if (source == NestedScrollSource.UserInput && availableY < 0) {
                                    return Offset(0f, scrollState.dispatchRawDelta(availableY))
                                }
                                return Offset.Zero
                            }

                            override fun onPostScroll(
                                consumed: Offset,
                                available: Offset,
                                source: NestedScrollSource
                            ): Offset {
                                val availableY = available.y
                                if (source == NestedScrollSource.UserInput && availableY > 0) {
                                    return Offset(0f, scrollState.dispatchRawDelta(availableY))
                                }
                                return Offset.Zero
                            }

                            override suspend fun onPreFling(available: Velocity): Velocity {
                                val availableY = available.y
                                if (availableY < 0) {
                                    val remainY = scrollState.fling(availableY, flingBehavior)
                                    return Velocity(0f, availableY - remainY)
                                }
                                return Velocity.Zero
                            }

                            override suspend fun onPostFling(
                                consumed: Velocity,
                                available: Velocity
                            ): Velocity {
                                val availableY = available.y
                                if (availableY > 0) {
                                    val remainY = scrollState.fling(availableY, flingBehavior)
                                    return Velocity(0f, availableY - remainY)
                                }
                                return Velocity.Zero
                            }
                        }
                    }
                )
        ) {
            DetailsInfo(
                song = song,
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        if (maxOffsetY == 0) {
                            scrollState.updateMaxValue(placeable.height)
                            maxOffsetY = placeable.height
                        }
                        layout(width = placeable.width, height = scrollState.height) {
                            placeable.place(0, scrollState.height - maxOffsetY)
                        }
                    }
            )
            DetailsPages(song)
        }
    }
}

class DetailsScrollState(
    val minValue: Int,
    var maxValue: Int
) : ScrollableState {
    var height by mutableIntStateOf(0)
        private set

    private var deferredConsumption = 0f

    private val scrollableState = ScrollableState { delta ->
        val consume = if (delta < 0) {
            max(minValue.toFloat() - height, delta)
        } else {
            min(maxValue.toFloat() - height, delta)
        }
        val currentConsume = consume + deferredConsumption
        val consumeInt = currentConsume.roundToInt()
        deferredConsumption = currentConsume - consumeInt
        height += consumeInt
        consume
    }

    override val isScrollInProgress: Boolean
        get() = scrollableState.isScrollInProgress

    override fun dispatchRawDelta(delta: Float): Float {
        return scrollableState.dispatchRawDelta(delta)
    }

    override suspend fun scroll(
        scrollPriority: MutatePriority,
        block: suspend ScrollScope.() -> Unit
    ) {
        scrollableState.scroll(scrollPriority, block)
    }

    fun updateMaxValue(value: Int) {
        maxValue = value
        height = maxValue
    }

    suspend fun fling(available: Float, flingBehavior: FlingBehavior): Float {
        var remainY = available
        scroll {
            with(flingBehavior) {
                remainY = performFling(available)
            }
        }
        return remainY
    }

}
