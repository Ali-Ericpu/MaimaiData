package org.plantalpha.maimaidata.feature.songdetails.component

import android.content.ClipData
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.plantalpha.maimaidata.R
import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.domain.model.SongVersion
import org.plantalpha.maimaidata.extension.toast
import org.plantalpha.maimaidata.feature.song.component.songImagePainter

@Preview
@Composable
fun DetailsInfo(
    modifier: Modifier = Modifier,
    song: Song = Song.song,
    alias: List<String> = emptyList()
) {
    val clipboard = LocalClipboard.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .background(song.basicInfo.genre.theme)
            .padding(12.dp)
    ) {
        Row(modifier = Modifier.wrapContentHeight()) {
            Box(modifier = Modifier.wrapContentSize()) {
                Image(
                    painter = songImagePainter(song.basicInfo.imageUrl),
                    contentDescription = "album cover",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(120.dp)
                )
                song.type.res?.let { res ->
                    Image(
                        painter = painterResource(res),
                        contentDescription = "Type",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(60.dp)
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)
                                layout(placeable.width, placeable.height) {
                                    placeable.place(0, placeable.height / 2 - placeable.height)
                                }
                            }
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                LabelToText(stringResource(R.string.song_genre), song.basicInfo.genre.type)
                LabelToText(stringResource(R.string.song_id), song.id.toString())
                LabelToText(stringResource(R.string.bpm), song.basicInfo.bpm.toString())
//                LabelToText(stringResource(R.string.song_version), stringResource(R.string.empty))
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    with(song.basicInfo) {
                        if (version != jpVersion) {
                            SongVersionImage(jpVersion)
                        }
                        SongVersionImage(version)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (alias.isNotEmpty()) {
            val aliasLabel = stringResource(R.string.song_alias)
            FlowRow {
                Text(
                    text = aliasLabel,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 16.dp)
                        .align(Alignment.CenterVertically)
                )
                val message = stringResource(R.string.copy_success)
                alias.forEach { alias ->
                    Text(
                        text = alias,
                        color = song.basicInfo.genre.theme,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(4.dp)
                            .background(
                                MaterialTheme.colorScheme.background,
                                RoundedCornerShape(16.dp)
                            )
                            .combinedClickable(
                                enabled = true,
                                indication = null,
                                interactionSource = null,
                                onClick = { },
                                onLongClick = {
                                    coroutineScope.launch {
                                        clipboard.setClipEntry(
                                            ClipData.newPlainText(aliasLabel, alias).toClipEntry()
                                        )
                                        context.toast(message.format(alias))
                                    }
                                }
                            )
                            .padding(horizontal = 4.dp))
                }
            }
        }
    }
}

@Composable
fun LabelToText(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = color,
        )
        Text(
            text = text,
            fontSize = 14.sp,
            maxLines = 1,
            color = color,
        )
    }
}

@Composable
private fun SongVersionImage(version: SongVersion) {
    Image(
        painter = painterResource(version.res),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.height(50.dp)
    )
}