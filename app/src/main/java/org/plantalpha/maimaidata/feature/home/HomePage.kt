package org.plantalpha.maimaidata.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.feature.home.component.SongCard

@Composable
fun HomePage() {
    val list = listOf(
        Song.song,
        Song.song,
        Song.song,
        Song.song,
        Song.song,
        Song.song,
        Song.song,
        Song.song,
        Song.song,
    )
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) { song ->
            SongCard(song)
        }
    }
}