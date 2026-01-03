package org.plantalpha.maimaidata.feature.song.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import org.plantalpha.maimaidata.R
import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.domain.model.SongGenre
import org.plantalpha.maimaidata.domain.model.SongVersion

@Preview
@Composable
fun SongSearchBar(
    modifier: Modifier = Modifier,
    searchData: Song.Search = Song.Search("", false, emptyList(), emptyList()),
    searchHistory: Set<String> = setOf("search", "history"),
    onBack: () -> Unit = {},
    onCleanHistory: () -> Unit = {},
    onSearch: (Song.Search) -> Unit = {}
) {
    var query by remember { mutableStateOf(searchData.text) }
    var isFavor by remember { mutableStateOf(searchData.favor) }
    val genreList = remember { mutableStateListOf(*searchData.genre.toTypedArray()) }
    val versionList = remember { mutableStateListOf(*searchData.version.toTypedArray()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            leadingIcon = { Icon(painterResource(R.drawable.ic_search), null) },
            placeholder = { Text(stringResource(R.string.search)) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton({ query = "" }) {
                        Icon(painterResource(R.drawable.ic_close), null)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(Song.Search(query, isFavor, genreList, versionList))
            }),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        ) {
            Text(stringResource(R.string.search_history))
            IconButton(onCleanHistory) {
                Icon(painterResource(R.drawable.ic_delete), null)
            }
        }
        FlowRow {
            searchHistory.forEach { history ->
                BubbleText(text = history, color = Color.LightGray)
            }
        }
        FlowRow(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.SpaceBetween,
            maxItemsInEachRow = 3,
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .border(2.dp, Color.Cyan.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            SongGenre.entries.fastForEach { genre ->
                val color = if (genre in genreList) genre.theme else Color.LightGray
                BubbleText(Modifier.weight(1f), genre.type, color) {
                    if (genre in genreList) genreList.remove(genre) else genreList.add(genre)
                }
            }
        }
        FlowRow(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.SpaceEvenly,
            maxItemsInEachRow = 4,
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            SongVersion.commonEntries.fastForEach { version ->
                val color = if (version in versionList)
                    MaterialTheme.colorScheme.primary
                else
                    Color.LightGray
                BubbleText(Modifier.weight(1f), version.value, color) {
                    if (version in versionList)
                        versionList.remove(version)
                    else
                        versionList.add(version)
                }
            }
        }
        BubbleText(
            text = stringResource(R.string.favor_song),
            color = if (isFavor) Color(0xFFF08080) else Color.LightGray,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp)
        ) {
            isFavor = !isFavor
        }
        BottomButton(
            onReset = {
                isFavor = false
                genreList.clear()
                versionList.clear()
                onSearch(Song.Search("", isFavor, genreList, versionList))
            }
        ) {
            onSearch(Song.Search(query, isFavor, genreList, versionList))
        }
        BackHandler {
            onBack()
        }
    }
}

@Composable
fun BubbleText(
    modifier: Modifier = Modifier,
    text: String = "Text",
    color: Color = Color.Transparent,
    onClick: () -> Unit = {}
) {
    var size by remember { mutableIntStateOf(14) }
    Text(
        text = text,
        fontSize = size.sp,
        maxLines = 1,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Visible,
        color = MaterialTheme.colorScheme.onBackground,
        onTextLayout = { result ->
            if (result.hasVisualOverflow) {
                size--
            }
        },
        modifier = modifier
            .padding(2.dp)
            .background(color, RoundedCornerShape(12.dp))
            .padding(4.dp)
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onClick
            )
            .wrapContentSize()
    )
}

@Composable
private fun BottomButton(
    modifier: Modifier = Modifier,
    onReset: () -> Unit,
    onSearch: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(onReset) {
            Text(stringResource(R.string.reset))
        }
        Button(onSearch) {
            Text(stringResource(R.string.search))
        }
    }

}