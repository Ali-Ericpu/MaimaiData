package org.plantalpha.maimaidata.network

import org.plantalpha.maimaidata.domain.model.Song
import org.plantalpha.maimaidata.domain.response.ChartsResponse
import org.plantalpha.maimaidata.domain.response.UpdateInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {

    @GET("update.json")
    suspend fun getUpdateInfo(): UpdateInfoResponse

    @GET("data_version.json")
    suspend fun getDataVersion(): UpdateInfoResponse

    @GET("data/song_list/{version}.json")
    suspend fun getSongList(@Path("version") version: String): List<Song>

    @GET("data/chart_stats/{version}.json")
    suspend fun getChartStats(@Path("version") version: String): ChartsResponse
}