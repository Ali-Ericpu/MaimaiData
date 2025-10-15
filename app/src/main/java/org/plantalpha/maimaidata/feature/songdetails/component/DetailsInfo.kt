package org.plantalpha.maimaidata.feature.songdetails.component

import android.content.ClipData
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import org.plantalpha.maimaidata.extension.toast
import org.plantalpha.maimaidata.feature.song.component.songImagePainter

@Preview
@Composable
fun DetailsInfo(
    modifier: Modifier = Modifier,
    song: Song = Song.song
) {
    val clipboard = LocalClipboard.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .background(song.basicInfo.genre.theme)
            .padding(12.dp)
    ) {
        Row {
            Image(
                painter = songImagePainter(song.basicInfo.imageUrl),
                contentDescription = "album cover",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(120.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LabelToText("歌曲分类", song.basicInfo.genre.type)
                LabelToText("歌曲ID", song.id.toString())
                LabelToText("BPM", song.basicInfo.bpm.toString())
                song.type.res?.let { res ->
                    Image(
                        painter = painterResource(res),
                        contentDescription = "Type",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .wrapContentHeight()
                            .align(Alignment.Start)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow {
            Text(
                text = "歌曲别名",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .align(Alignment.CenterVertically)
            )
            val message = stringResource(R.string.copy_success)
            song.alias.forEach { alias ->

                Text(
                    text = alias,
                    color = song.basicInfo.genre.theme,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp)
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp))
                        .combinedClickable(
                            enabled = true,
                            onClick = { },
                            onLongClick = {
                                coroutineScope.launch {
                                    clipboard.setClipEntry(
                                        ClipData.newPlainText("", alias).toClipEntry()
                                    )
                                }
                                context.toast(message.format(alias))
                            }
                        )
                        .padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp))

            }
        }
    }
}

@Preview
@Composable
fun LabelToText(
    label: String = "Label",
    text: String = "text",
    color: Color = MaterialTheme.colorScheme.background
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = text,
            maxLines = 1,
            color = color,
            modifier = Modifier.weight(4f)
        )
    }
}