package org.plantalpha.maimaidata.domain.model

import androidx.compose.ui.graphics.Color
import com.fasterxml.jackson.annotation.JsonCreator

enum class SongGenre(val type: String, val theme: Color) {
    POPULAR("流行&动漫", Color(0xFFF8B709)),
    NICO_NICO("niconico & VOCALOID", Color(0xFF12B4FF)),
    TOUHOU_PROJECT("东方Project", Color(0xFF9F51DC)),
    OTHER("其他游戏", Color(0xFF6FD43D)),
    MAIMAI("舞萌", Color(0xFFFF4646)),
    ACG("音击&中二节奏", Color(0xFF319DF8)),
    U_TAGE("宴•会•场", Color(0xFFDC39B8));

    companion object {
        @JsonCreator
        @JvmStatic
        fun valueForm(type: String): SongGenre {
            return entries.find { genre -> genre.type == type } ?: OTHER
        }
    }
}
