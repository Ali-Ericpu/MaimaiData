package org.plantalpha.maimaidata.feature.song

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.feature.song.component.SongCard
import org.plantalpha.maimaidata.feature.song.component.UpdateDataVersionDialog
import org.plantalpha.maimaidata.feature.song.viewmodel.SongListViewModel

@Composable
fun SongListPage(
    viewModel: SongListViewModel = hiltViewModel(),
    navToDetail: (Song) -> Unit = { }
) {
    val songs by viewModel.songList.collectAsStateWithLifecycle()
    val showUpdateDialog by viewModel.showUpdateDialog.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = viewModel::checkDataVersion,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(songs) { song ->
                    SongCard(song) { navToDetail(song) }
                }
            }
        }
        if (showUpdateDialog) {
            UpdateDataVersionDialog(
                content = viewModel.updateContent,
                onCancel = viewModel::changeShowUpdateDataDialogState,
            ) {
                viewModel.changeShowUpdateDataDialogState()
                viewModel.updateVersion()
            }
        }
    }
}