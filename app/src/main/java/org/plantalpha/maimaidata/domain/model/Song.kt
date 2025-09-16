package org.plantalpha.maimaidata.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

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
    data class BasicInfo(
        val bpm: Int,
        val artist: String,
        val genre: String,
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

    data class UtageInfo(
        val kanji: String,
        val comment: String,
        val buddy: Boolean
    )

    data class Chart(
        val charter: String,
        val level: String,
        @JsonProperty("internal_level")
        val internalLevel: Double,
        @JsonProperty("old_internal_level")
        val oldInternalLevel: Double?,
        val notes: NoteInfo
    )

    data class NoteInfo(
        val tap: Int,
        val hold: Int,
        val slide: Int,
        val torch: Int?,
        val `break`: Int
    )

    enum class Type {
        SD,
        DX,
        UTAGE
    }

    companion object {
        val song = Song(
            1,
            1,
            "Test",
            "TEST",
            Type.DX,
            0,
            listOf("2333", "test"),
            BasicInfo(
                114,
                "Test artist",
                "niconico & VOCALOID",
                "1",
                "1.1",
                true,
                "507c390321c312eb.png",
                UtageInfo("test", "tttttest", true)
            ),
            listOf()
        )
    }
}