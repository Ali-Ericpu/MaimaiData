package org.plantalpha.maimaidata.feature.songdetails

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.plantalpha.maimaidata.repository.SongDatabase
import javax.inject.Inject

@HiltViewModel
class SongDetailsViewModel @Inject constructor(
    val songDatabase: SongDatabase,
): ViewModel() {
    private val songDao = songDatabase.songDao()

}