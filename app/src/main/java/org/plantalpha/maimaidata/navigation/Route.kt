package org.plantalpha.maimaidata.navigation

import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.plantalpha.maimaidata.R
import org.plantalpha.maimaidata.domain.model.Song

@Serializable
sealed class Route(@StringRes val desc: Int) : NavKey {

    @Serializable
    data object Home: Route(R.string.home)

    @Serializable
    data class SongDetail(val song: Song, val alias: List<String>): Route(R.string.song_detail)

    @Serializable
    data object Rating : Route(R.string.rating)

}