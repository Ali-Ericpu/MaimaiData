package org.plantalpha.maimaidata.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object Networker {
    const val BASE_URL = "https://maimaidata.violetc.net/"
    const val IMAGE_URL = "https://maimai.wahlap.com/maimai-mobile/img/Music/"
    const val DATA_BASE_VERSION = 3

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val service = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(NetworkService::class.java)

    suspend fun getDataVersion(): String {
        return service.getDataVersion()[DATA_BASE_VERSION.toString()]!!.version
    }


    suspend fun getUpdateInfo() = service.getUpdateInfo()

    suspend fun getSongList(version: String) = service.getSongList(version)

}