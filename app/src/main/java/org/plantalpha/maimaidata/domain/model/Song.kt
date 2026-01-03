package org.plantalpha.maimaidata.domain.model

import androidx.annotation.DrawableRes
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.plantalpha.maimaidata.R
import org.plantalpha.maimaidata.domain.model.Song.ChartTypeConvert

@Entity
@Serializable
@TypeConverters(ChartTypeConvert::class)
data class Song(
    @PrimaryKey
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
    @Embedded
    val basicInfo: BasicInfo,
    val charts: List<Chart>
) {

    @Serializable
    data class BasicInfo(
        val bpm: Int,
        val artist: String,
        val genre: SongGenre,
        val version: SongVersion,
        @SerialName("jp_version")
        val jpVersion: SongVersion,
        @SerialName("is_new")
        val isNew: Boolean,
        @SerialName("image_url")
        val imageUrl: String,
        @SerialName("utage_info")
        @Embedded
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
                SongVersion.MAIMAI,
                SongVersion.MAIMAI_PLUS,
                true,
                "507c390321c312eb.png",
                UtageInfo("test", "tttttest", true)
            ),
            listOf(

            )
        )
        val versions = listOf(
            "maimai",
            "FiNALE",
            "GreeN",
            "GreeN PLUS",
            "ORANGE",
            "ORANGE PLUS",
            "PiNK",
            "PiNK PLUS",
            "MURASAKi",
            "MURASAKi PLUS",
            "MiLK",
            "MiLK PLUS",
            "舞萌DX",
            "舞萌DX 2021",
            "舞萌DX 2022",
            "舞萌DX 2023",
            "舞萌DX 2024",
            "舞萌DX 2025"
        )
    }

    @Serializable
    data class Search(
        val text: String,
        val favor: Boolean,
        val genre: List<SongGenre>,
        val version: List<SongVersion>
    )

    class ChartTypeConvert {

        @TypeConverter
        fun toJson(value: List<Chart>): String {
            return Json.encodeToString(value)
        }

        @TypeConverter
        fun fromJson(value: String): List<Chart> {
            return Json.decodeFromString(value)
        }

    }
}