package org.plantalpha.maimaidata.feature.song.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.network.Networker
import java.net.URLEncoder

class SongListViewModel : ViewModel() {
    private val _songList = MutableStateFlow(listOf<Song>())
    val songList = _songList.asStateFlow()

    suspend fun updateSongData() = withContext(Dispatchers.IO) {
        _songList.value =
            Networker.getSongList(URLEncoder.encode("CN1.51-E-fix2", "UTF-8"))
    }

}