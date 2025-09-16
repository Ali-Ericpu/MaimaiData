package org.plantalpha.maimaidata.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.plantalpha.maimaidata.feature.home.component.SongCard
import org.plantalpha.maimaidata.feature.home.viewmodel.HomeViewModel

@Composable
fun HomePage(
    viewModel: HomeViewModel = viewModel()
) {
    val list by viewModel.songList.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.updateSongData()
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) { song ->
            SongCard(song)
        }
    }
}