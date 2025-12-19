package org.plantalpha.maimaidata.feature.song.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.plantalpha.maimaidata.R
import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.extension.toast
import org.plantalpha.maimaidata.network.Networker
import org.plantalpha.maimaidata.repository.DataRepository
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(
    val dataRepository: DataRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    private var version = ""
    private var latestVersion = ""

    private var songList = listOf<Song>()

    private val _sortedSongList = MutableStateFlow(listOf<Song>())
    val sortedSongList = _sortedSongList.asStateFlow()

    private val _showUpdateDialog = MutableStateFlow(false)
    val showUpdateDialog = _showUpdateDialog.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchData = MutableStateFlow(Song.Search("", false, emptyList(), emptyList()))
    val searchData = _searchData.asStateFlow()

    val searchHistory = dataRepository.getSearchHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val updateContent
        get() = context.getString(R.string.update_data_version_content)
            .format(version.ifEmpty { "无" }, latestVersion)

    init {
        viewModelScope.launch {
            dataRepository.saveVersion("")  //测试使用
            launch {
                dataRepository.getVersion().collect {
                    version = it
                    Log.d("VERSION", "DataVersion: $it")
                }
            }
            checkDataVersion()
        }
    }

    fun checkDataVersion() {
        viewModelScope.launch {
            _isRefreshing.value = true
            runCatching {
                Networker.getDataVersion()
            }.onSuccess {
                if (version != it) {
                    latestVersion = it
                    _showUpdateDialog.value = true
                    Log.d("VERSION", "Latest DataVersion: $it")
                } else {
                    context.toast(R.string.already_latest_version)
                }
            }.onFailure {
                Log.d("ERROR", "Update Version Error: ${it.message}")
                context.toast(R.string.version_err)
            }
            _isRefreshing.value = false
        }
    }

    fun updateVersion() {
        viewModelScope.launch {
            if (latestVersion.isNotEmpty() && latestVersion != version) {
                dataRepository.saveVersion(latestVersion)
                updateSongData()
            }
        }
    }

    fun loadSongData() = viewModelScope.launch {
        TODO("From Room DB")
    }

    fun updateSongData() = viewModelScope.launch {
        songList = Networker.getSongList(URLEncoder.encode(version, "UTF-8"))
        _sortedSongList.value = songList.sortedBy { it.sortId }
        _searchData.value = Song.Search("", false, emptyList(), emptyList())
    }

    fun changeShowUpdateDataDialogState() = viewModelScope.launch {
        _showUpdateDialog.value = !_showUpdateDialog.value
    }

    fun onSearch(search: Song.Search) = viewModelScope.launch {
        if (search.text.isNotBlank()) {
            dataRepository.saveSearchHistory(searchHistory.value + search.text)
        }
        _searchData.value = search
        _sortedSongList.value = songList.filter { song ->
//            search.favor && song.isFavor ||
            (search.text.isBlank() || search.text in song.title) &&
                    (search.genre.isEmpty() || song.basicInfo.genre in search.genre) &&
                    (search.version.isEmpty() || song.basicInfo.version in search.version)
        }
        changeSearchState()
    }

    fun changeSearchState() = viewModelScope.launch {
        _isSearching.value = !_isSearching.value
    }

    fun cleanSearchHistory() = viewModelScope.launch {
        dataRepository.saveSearchHistory(emptySet())
    }
}