package org.plantalpha.maimaidata.repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataRepository @Inject constructor(val dataStoreManager: DataStoreManager) {

    suspend fun saveVersion(version: String) {
        dataStoreManager.saveVersion(version)
    }

    fun getVersion(): Flow<String> = dataStoreManager.versionFlow()

}