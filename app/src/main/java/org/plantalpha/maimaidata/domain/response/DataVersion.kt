package org.plantalpha.maimaidata.domain.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataVersion(
    val version: String,
    @SerialName("song_list_url")
    val songListUrl: String,
    @SerialName("chart_alias_url")
    val chartAliasUrl: String? = null,
    @SerialName("chart_stats_url")
    val chartStatsUrl: String
)