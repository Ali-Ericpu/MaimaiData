package org.plantalpha.maimaidata.domain.model

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = SongGenreSerializer::class)
enum class SongGenre(val type: String, val theme: Color) {
    POPULAR("流行&动漫", Color(0xFFF8B709)),
    TOUHOU_PROJECT("东方Project", Color(0xFF9F51DC)),
    OTHER("其他游戏", Color(0xFF6FD43D)),
    ACG("音击&中二节奏", Color(0xFF319DF8)),
    MAIMAI("舞萌", Color(0xFFFF4646)),
    U_TAGE("宴·会·场", Color(0xFFDC39B8)),
    NICO_NICO("niconico & VOCALOID", Color(0xFF12B4FF));
}

class SongGenreSerializer : KSerializer<SongGenre> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SongGenre", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): SongGenre {
        val desc = decoder.decodeString()
        return SongGenre.entries.find { it.type == desc } ?: SongGenre.OTHER
    }

    override fun serialize(encoder: Encoder, value: SongGenre) {
        encoder.encodeString(value.type)
    }
}