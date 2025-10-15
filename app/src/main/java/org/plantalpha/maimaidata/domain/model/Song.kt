package org.plantalpha.maimaidata.domain.model

import androidx.annotation.DrawableRes
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable
import org.plantalpha.maimaidata.R

@Serializable
data class Song(
    val id: Int,
    @JsonProperty("sort_id")
    val sortId: Int?,
    val title: String,
    @JsonProperty("title_kana")
    val titleKana: String,
    val type: Type,
    @JsonProperty("release_time")
    val releaseTime: Int,
    val alias: List<String>,
    @JsonProperty("basic_info")
    val basicInfo: BasicInfo,
    val charts: List<Chart>,
) {

    @Serializable
    data class BasicInfo(
        val bpm: Int,
        val artist: String,
        val genre: SongGenre,
        val version: String,
        @JsonProperty("jp_version")
        val jpVersion: String,
        @JsonProperty("is_new")
        val isNew: Boolean,
        @JsonProperty("image_url")
        val imageUrl: String,
        @JsonProperty("utage_info")
        val utageInfo: UtageInfo?
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
        @JsonProperty("internal_level")
        val internalLevel: Double,
        @JsonProperty("old_internal_level")
        val oldInternalLevel: Double?,
        val notes: NoteInfo
    )

    @Serializable
    data class NoteInfo(
        val tap: Int,
        val hold: Int,
        val slide: Int,
        val torch: Int?,
        val `break`: Int
    ) {
        val entries = listOf(
            "Total" to tap + hold + slide + (torch ?: 0) + `break`,
            "Tab" to tap,
            "Hold" to hold,
            "Slide" to slide,
            "Torch" to (torch ?: 0),
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
            listOf(
                "2333",
                "test",
                "E2we1",
                "E2eada",
                "Ed144",
                "Edwqdi",
                "Edwqdi",
                "Edwqdi",
                "Edwqdi"
            ),
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