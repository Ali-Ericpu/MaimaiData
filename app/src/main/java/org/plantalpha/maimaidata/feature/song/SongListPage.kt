package org.plantalpha.maimaidata.feature.song

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.feature.song.component.SongCard
import org.plantalpha.maimaidata.feature.song.component.SongSearchBar
import org.plantalpha.maimaidata.feature.song.component.SongTopBar
import org.plantalpha.maimaidata.feature.song.component.UpdateDataVersionDialog
import org.plantalpha.maimaidata.feature.song.viewmodel.SongListViewModel

@Composable
fun SongListPage(
    viewModel: SongListViewModel = hiltViewModel(),
    navToDetail: (Song, List<String>) -> Unit = { _, _ -> }
) {
    val songs by viewModel.sortedSongList.collectAsStateWithLifecycle()
    val showUpdateDialog by viewModel.showUpdateDialog.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val searchData by viewModel.searchData.collectAsStateWithLifecycle()
    val searchHistory by viewModel.searchHistory.collectAsStateWithLifecycle(emptySet())
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = viewModel::checkDataVersion,
        modifier = Modifier.statusBarsPadding().fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SongTopBar(viewModel::changeSearchState) {
                if (songs.isNotEmpty()) {
                    val song = songs.random()
                    navToDetail(song, viewModel.getAlias(song.id))
                }
            }
            AnimatedVisibility(isSearching) {
                SongSearchBar(
                    searchData = searchData,
                    searchHistory = searchHistory,
                    onBack = viewModel::changeSearchState,
                    onCleanHistory = viewModel::cleanSearchHistory,
                ) {
                    scope.launch { state.animateScrollToItem(0) }
                    viewModel.onSearch(it)
                }
            }
            LazyColumn(state = state, modifier = Modifier.fillMaxSize()) {
                items(songs) { song ->
                    SongCard(song) { navToDetail(song, viewModel.getAlias(song.id)) }
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