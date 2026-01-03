package org.plantalpha.maimaidata.network

import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.domain.response.ChartsAliasInfo
import org.plantalpha.maimaidata.domain.response.ChartsStatsInfo
import org.plantalpha.maimaidata.domain.response.DataVersion
import org.plantalpha.maimaidata.domain.response.UpdateInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface SongApi {

    @GET("update.json")
    suspend fun getUpdateInfo(): UpdateInfo

    @GET("data_version.json")
    suspend fun getDataVersion(): Map<String, DataVersion>

    @GET("data/song_list/{version}.json")
    suspend fun getSongList(@Path("version") version: String): List<Song>

    @GET("data/chart_alias/{version}.json")
    suspend fun getChartAlias(@Path("version") version: String): ChartsAliasInfo

    @GET("data/chart_stats/{version}.json")
    suspend fun getChartStats(@Path("version") version: String): ChartsStatsInfo
}