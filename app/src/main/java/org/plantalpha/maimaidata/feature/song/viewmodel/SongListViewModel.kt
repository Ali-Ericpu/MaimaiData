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
import org.plantalpha.maimaidata.network.SongApi
import org.plantalpha.maimaidata.repository.DataRepository
import org.plantalpha.maimaidata.repository.SongDatabase
import org.plantalpha.maimaidata.util.Constants.DATA_BASE_VERSION
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(
    val dataRepository: DataRepository,
    val songDatabase: SongDatabase,
    val songApi: SongApi,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val songDao = songDatabase.songDao()

    private val version = dataRepository.getVersion()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")
    private var latestVersion = ""

    private var songList = listOf<Song>()

    private val aliasData = mutableMapOf<Int, List<String>>()

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
            .format(version.value.ifEmpty { "无" }, latestVersion)

    init {
        viewModelScope.launch {
            checkDataVersion()
            loadSongData()
        }
    }

    fun checkDataVersion() {
        viewModelScope.launch {
            _isRefreshing.value = true
            runCatching {
                songApi.getDataVersion()[DATA_BASE_VERSION.toString()]!!.version
            }.onSuccess { dataVersion ->
                if (version.value != dataVersion) {
                    latestVersion = dataVersion
                    _showUpdateDialog.value = true
                    Log.d("VERSION", "Latest DataVersion: $dataVersion")
                } else if (songList.isNotEmpty()){
                    context.toast(R.string.already_latest_version)
                }
            }.onFailure {
                Log.d("ERROR", "Update Version Error: ${it.message}")
                context.toast(R.string.update_version_err)
            }
            _isRefreshing.value = false
        }
    }

    fun updateVersion() {
        viewModelScope.launch {
            if (latestVersion.isNotEmpty() && latestVersion != version.value) {
                dataRepository.saveVersion(latestVersion)
                updateSongData()
            }
        }
    }

    fun updateAliasData() {
        viewModelScope.launch {
            runCatching {
                songApi.getChartAlias(version.value)
            }.onSuccess {
                it.aliases.forEach { alias ->
                    aliasData[alias.id] = alias.alias
                }
            }.onFailure {
                Log.d("ERROR", "Update Alias Data Error: ${it.message}")
                context.toast(R.string.update_alias_err)
            }
        }
    }

    fun loadSongData() = viewModelScope.launch {
        _isRefreshing.value = true
        songList = songDao.getAll()
        if (songList.isNotEmpty()) {
            updateAliasData()
        }
        _sortedSongList.value = songList.sortedBy { it.sortId }
        _isRefreshing.value = false
    }

    fun updateSongData() = viewModelScope.launch {
        Log.d("UPDATE", "updateSongData: ${version.value}")
        songList = songApi.getSongList(URLEncoder.encode(version.value, "UTF-8"))
        songDao.deleteAll()
        songDao.insertAll(songList)
        _sortedSongList.value = songList.sortedBy { it.sortId }
        updateAliasData()
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

    fun getAlias(id: Int): List<String> = aliasData[id] ?: emptyList()
}