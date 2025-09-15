package org.plantalpha.maimaidata.domain.model

import androidx.compose.ui.graphics.Color

sealed class SongGenre(val type: String, val theme: Color) {
    object Popular : SongGenre("流行&动漫", Color(0xFFF8B709))
    object NicoNico : SongGenre("niconico & VOCALOID", Color(0xFF12B4FF))
    object TouhouProject : SongGenre("东方Project", Color(0xFF9F51DC))
    object Other : SongGenre("其他游戏", Color(0xFF6FD43D))
    object Maimai : SongGenre("舞萌", Color(0xFFFF4646))
    object Acg : SongGenre("音击&中二节奏", Color(0xFF319DF8))
    object Utage : SongGenre("宴•会•场", Color(0xFFDC39B8))

    companion object {
        val entries = listOf(Popular, NicoNico, TouhouProject, Other, Maimai, Acg, Utage)
        val map = entries.associateBy { it.type }

        fun findThemeColor(type: String): Color = map[type]!!.theme
    }
}
