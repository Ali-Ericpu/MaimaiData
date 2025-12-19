package org.plantalpha.maimaidata.feature.song.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.plantalpha.maimaidata.R

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongTopBar(
    onClickSearch: () -> Unit = {},
    onClickRandom: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(stringResource(R.string.home)) },
        actions = {
            IconButton(onClickRandom) {
                Icon(painterResource(R.drawable.ic_shuffle), null)
            }
            IconButton(onClickSearch) {
                Icon(painterResource(R.drawable.ic_search), null)
            }
        }
    )
}