package org.plantalpha.maimaidata.feature.songdetails

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.plantalpha.maimaidata.domain.model.MaxChartNote
import org.plantalpha.maimaidata.repository.DataRepository
import org.plantalpha.maimaidata.repository.SongDatabase
import javax.inject.Inject

@HiltViewModel
class SongDetailsViewModel @Inject constructor(
    val songDatabase: SongDatabase,
    val dataRepository: DataRepository
) : ViewModel() {
    private val chartNoteDao = songDatabase.chartNoteDao()
    private val version = dataRepository.getVersion()

    private var _maxChartNote = MutableStateFlow(MaxChartNote())
    val maxChartNote = _maxChartNote.asStateFlow()

    init {
        viewModelScope.launch {
            version.collect {
                chartNoteDao.getMaxChartNote(it)?.let { note ->
                    _maxChartNote.value = note
                }
            }
        }
    }

    private val tabs = mutableStateListOf("Re:MAS", "MAS", "EXP", "ADV", "BAS")

    fun tabs(size: Int): List<String> {
        if (size < tabs.size) {
            tabs.removeAt(0)
        }
        return tabs
    }


}