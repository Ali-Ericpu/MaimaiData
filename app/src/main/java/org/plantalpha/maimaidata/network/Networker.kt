package org.plantalpha.maimaidata.network

import org.plantalpha.maimaidata.util.JsonUtil
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object Networker {
    const val BASE_URL = "https://maimaidata.violetc.net/"
    const val IMAGE_URL = "https://maimai.wahlap.com/maimai-mobile/img/Music/"

    private val service =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create(JsonUtil.mapper))
        .build()
        .create(NetworkService::class.java)

    suspend fun getUpdateInfo() = service.getUpdateInfo()

    suspend fun getSongList(version: String) = service.getSongList(version)

}