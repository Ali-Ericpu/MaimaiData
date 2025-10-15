package org.plantalpha.maimaidata.feature.song

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.plantalpha.maimaidata.feature.song.component.SongCard
import org.plantalpha.maimaidata.feature.song.viewmodel.SongListViewModel

@Composable
fun SongListPage(
    viewModel: SongListViewModel = viewModel(),
    navToDetail: (Int) -> Unit = { }
) {
    val songs by viewModel.songList.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.updateSongData()
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(songs.size) { index ->
            SongCard(songs[index]) { navToDetail(index) }
        }
    }
}