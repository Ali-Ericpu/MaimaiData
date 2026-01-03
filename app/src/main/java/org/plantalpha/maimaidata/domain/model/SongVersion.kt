package org.plantalpha.maimaidata.domain.model

import androidx.annotation.DrawableRes
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.plantalpha.maimaidata.R

@Serializable(with = SongVersionSerializer::class)
enum class SongVersion(val value: String, val isJpOnly: Boolean, @DrawableRes val res: Int) {
    MAIMAI("maimai", false, R.drawable.maimai),
    MAIMAI_PLUS("maimai PLUS", true, R.drawable.maimai_plus),
    FiNALE("FiNALE", false, R.drawable.maimai_finale),
    GreeN("GreeN", false, R.drawable.maimai_green),
    GreeN_PLUS("GreeN PLUS", false, R.drawable.maimai_green_plus),
    ORANGE("ORANGE", false, R.drawable.maimai_orange),
    ORANGE_PLUS("ORANGE PLUS", false, R.drawable.maimai_orange_plus),
    PiNK("PiNK", false, R.drawable.maimai_pink),
    PiNK_PLUS("PiNK PLUS", false, R.drawable.maimai_pink_plus),
    MURASAKi("MURASAKi", false, R.drawable.maimai_murasaki),
    MURASAKi_PLUS("MURASAKi PLUS", false, R.drawable.maimai_murasaki_plus),
    MiLK("MiLK", false, R.drawable.maimai_milk),
    MiLK_PLUS("MiLK PLUS", false, R.drawable.maimai_milk_plus),
    FESTiVAL("FESTiVAL", true, R.drawable.maimaidx_festival),
    FESTiVAL_PLUS("FESTiVAL PLUS", true, R.drawable.maimaidx_festival_plus),
    UNiVERSE("UNiVERSE", true, R.drawable.maimaidx_universe),
    UNiVERSE_PLUS("UNiVERSE PLUS", true, R.drawable.maimaidx_universe_plus),
    SPLASH("Splash", true, R.drawable.maimaidx_splash),
    SPLASH_PLUS("Splash PLUS", true, R.drawable.maimaidx_splash_plus),
    BUDDiES("BUDDiES", true, R.drawable.maimaidx_buddies),
    BUDDiES_PLUS("BUDDiES PLUS", true, R.drawable.maimaidx_buddies_plus),
    PRiSM("PRiSM", true, R.drawable.maimaidx_prism),
    MAIMAIDX("maimaiでらっくす", true, R.drawable.maimaidx),
    MAIMAIDX_PLUS("maimaiでらっくす PLUS", true, R.drawable.maimaidx_plus),
    MAIMAIDX_CN("舞萌DX", false, R.drawable.maimaidx_cn),
    MAIMAIDX_2021("舞萌DX 2021", false, R.drawable.maimaidx_2021),
    MAIMAIDX_2022("舞萌DX 2022", false, R.drawable.maimaidx_2022),
    MAIMAIDX_2023("舞萌DX 2023", false, R.drawable.maimaidx_2023),
    MAIMAIDX_2024("舞萌DX 2024", false, R.drawable.maimaidx_2024),
    MAIMAIDX_2025("舞萌DX 2025", false, R.drawable.maimaidx_2025), ;

    companion object {
        val commonEntries get() = entries.filter { !it.isJpOnly }
    }
}

class SongVersionSerializer : KSerializer<SongVersion> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SongVersion", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): SongVersion {
        val value = decoder.decodeString()
        return SongVersion.entries.find { it.value == value } ?: SongVersion.MAIMAI
    }

    override fun serialize(encoder: Encoder, value: SongVersion) {
        encoder.encodeString(value.value)
    }
}