package org.plantalpha.maimaidata.feature.home.component

import org.plantalpha.maimaidata.R
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.rememberAsyncImagePainter
import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.domain.model.SongGenre
import org.plantalpha.maimaidata.network.Networker
import org.plantalpha.maimaidata.ui.theme.chartsScoreColors

@Preview
@Composable
fun SongCard(songData: Song = Song.song) {
    val theme = SongGenre.findThemeColor(songData.basicInfo.genre)
    ConstraintLayout(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .padding(16.dp)
            .height(180.dp)
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp))
    ) {
        val (imageRef, titleRef, scoreRef, typeRef, genreRef, backgroundRef, themeColorRef) = createRefs()
        Spacer(
            modifier = Modifier
                .fillMaxHeight(0.55f)
                .padding(6.dp)
                .fillMaxWidth()
                .background(theme)
                .constrainAs(themeColorRef) {
                    top.linkTo(backgroundRef.top)
                }
        )
        Spacer(
            modifier = Modifier
                .constrainAs(backgroundRef) {
                    centerTo(parent)
                }
                .fillMaxSize()
                .border(3.dp, Color.White, RoundedCornerShape(16.dp))
                .border(6.dp, theme, RoundedCornerShape(16.dp))
        )
        GenreTag(
            genre = songData.basicInfo.genre,
            color = theme,
            modifier = Modifier.constrainAs(genreRef) {
                top.linkTo(backgroundRef.top, (-18).dp)
                end.linkTo(backgroundRef.end, 12.dp)
                width = Dimension.percent(0.45f)
                height = Dimension.percent(0.18f)
            }
        )
        Image(
            painter = songImagePainter(songData.basicInfo.imageUrl),
            contentDescription = "album cover",
            modifier = Modifier
                .size(92.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(3.dp, theme, RoundedCornerShape(8.dp))
                .constrainAs(imageRef) {
                    top.linkTo(backgroundRef.top, 16.dp)
                    start.linkTo(backgroundRef.start, 16.dp)
                }
        )
        Column(
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(imageRef.top, 4.dp)
                start.linkTo(imageRef.end, 12.dp)
            }
        ) {
            Text(
                text = songData.title,
                color = MaterialTheme.colorScheme.background,
                fontSize = 16.sp
            )
            Text(
                text = songData.basicInfo.artist,
                color = MaterialTheme.colorScheme.background,
                fontSize = 6.sp,
            )
        }
        when (songData.type) {
            Song.Type.SD -> R.drawable.ic_song_type_sd
            Song.Type.DX -> R.drawable.ic_song_type_dx
            Song.Type.UTAGE -> null
        }?.let { res ->
            Image(
                painter = painterResource(res),
                contentDescription = "Type",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(typeRef) {
                        bottom.linkTo(themeColorRef.bottom, 12.dp)
                        end.linkTo(themeColorRef.end, 12.dp)
                        width = Dimension.percent(0.2f)
                    }
            )
        }
        ChartLevelRow(
            scoreList = songData.charts.map { it.internalLevel },
            modifier = Modifier.constrainAs(scoreRef) {
                bottom.linkTo(backgroundRef.bottom, 12.dp)
                end.linkTo(backgroundRef.end, 12.dp)
            }
        )
    }
}

@Preview
@Composable
fun GenreTag(
    modifier: Modifier = Modifier,
    genre: String = "Genre",
    color: Color = Color.Cyan
) {
    var textSize by remember { mutableIntStateOf(14) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(36.dp)
            .background(color, RoundedCornerShape(40.dp))
            .border(2.dp, Color.White, RoundedCornerShape(40.dp))
            .then(modifier)
    ) {
        Text(
            genre,
            color = MaterialTheme.colorScheme.background,
            fontSize = textSize.sp,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Visible,
            onTextLayout = { result ->
                if (result.hasVisualOverflow) {
                    textSize--
                }
            },
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
    }

}

@Preview
@Composable
fun ScoreCard(
    score: String = "0.0",
    color: Color = Color.Cyan
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(2.dp)
            .size(48.dp)
            .background(
                if (score.isNotBlank()) color else Color.Unspecified,
                RoundedCornerShape(8.dp)
            )
            .border(1.dp, color, RoundedCornerShape(8.dp))
            .border(2.dp, Color.White, RoundedCornerShape(8.dp))
    ) {
        Text(score, color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.ExtraBold)
    }
}

@Preview
@Composable
fun ChartLevelRow(
    modifier: Modifier = Modifier,
    scoreList: List<Double> = listOf(12.0, 12.1, 11.0, 9.0)
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(5) { index ->
            ScoreCard(
                score = scoreList.getOrNull(index)?.toString() ?: "",
                color = chartsScoreColors[index]
            )
        }
    }
}

@Composable
fun songImagePainter(imageId: String): Painter {
    Log.d("IMAGE", "songImagePainter: ${Networker.IMAGE_URL}$imageId")
    return rememberAsyncImagePainter(Networker.IMAGE_URL + imageId)
}
