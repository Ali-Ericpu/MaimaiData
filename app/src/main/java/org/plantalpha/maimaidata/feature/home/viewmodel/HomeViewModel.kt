package org.plantalpha.maimaidata.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.network.Networker
import java.net.URLEncoder

class HomeViewModel : ViewModel() {
    private val _songList = MutableStateFlow(mutableListOf<Song>())
    val songList = _songList.asStateFlow()

    fun updateSongData() = viewModelScope.launch {
        _songList.value =
            Networker.getSongList(URLEncoder.encode("CN1.51-E-fix2", "UTF-8")).toMutableList()
    }

}