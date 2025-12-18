package org.plantalpha.maimaidata.domain.model

import androidx.annotation.DrawableRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.plantalpha.maimaidata.R

@Serializable
data class Song(
    val id: Int,
    @SerialName("sort_id")
    val sortId: Int?,
    val title: String,
    @SerialName("title_kana")
    val titleKana: String,
    val type: Type,
    @SerialName("release_time")
    val releaseTime: Int,
    @SerialName("basic_info")
    val basicInfo: BasicInfo,
    val charts: List<Chart>,
) {

    @Serializable
    data class BasicInfo(
        val bpm: Int,
        val artist: String,
        val genre: SongGenre,
        val version: String,
        @SerialName("jp_version")
        val jpVersion: String,
        @SerialName("is_new")
        val isNew: Boolean,
        @SerialName("image_url")
        val imageUrl: String,
        @SerialName("utage_info")
        val utageInfo: UtageInfo? = null
    )

    @Serializable
    data class UtageInfo(
        val kanji: String,
        val comment: String,
        val buddy: Boolean
    )

    @Serializable
    data class Chart(
        val charter: String,
        val level: String,
        @SerialName("internal_level")
        val internalLevel: Double,
        @SerialName("old_internal_level")
        val oldInternalLevel: Double?,
        val notes: NoteInfo
    )

    @Serializable
    data class NoteInfo(
        val tap: Int,
        val hold: Int,
        val slide: Int,
        val touch: Int? = null,
        val `break`: Int
    ) {
        fun entries(): List<Pair<String, Int>> = listOf(
            "Total" to tap + hold + slide + (touch ?: 0) + `break`,
            "Tab" to tap,
            "Hold" to hold,
            "Slide" to slide,
            "Touch" to (touch ?: 0),
            "Break" to `break`
        )
    }

    enum class Type(@DrawableRes val res: Int?) {
        SD(R.drawable.ic_song_type_sd),
        DX(R.drawable.ic_song_type_dx),
        UTAGE(null)
    }

    companion object {
        val song = Song(
            1,
            1,
            "Test",
            "TEST",
            Type.DX,
            0,
            BasicInfo(
                114,
                "Test artist",
                SongGenre.NICO_NICO,
                "1",
                "1.1",
                true,
                "507c390321c312eb.png",
                UtageInfo("test", "tttttest", true)
            ),
            listOf(

            )
        )
    }
}