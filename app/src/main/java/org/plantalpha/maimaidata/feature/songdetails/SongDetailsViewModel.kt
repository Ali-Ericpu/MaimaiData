package org.plantalpha.maimaidata.feature.songdetails

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.plantalpha.maimaidata.repository.SongDatabase
import javax.inject.Inject

@HiltViewModel
class SongDetailsViewModel @Inject constructor(
    val songDatabase: SongDatabase,
) : ViewModel() {
    private val songDao = songDatabase.songDao()

    private val tabs = mutableStateListOf("Re:MAS", "MAS", "EXP", "ADV", "BAS")

    fun tabs(size: Int): List<String> {
        if (size < tabs.size) {
            tabs.removeAt(0)
        }
        return tabs
    }


}