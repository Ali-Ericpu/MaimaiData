package org.plantalpha.maimaidata.repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataRepository @Inject constructor(val dataStoreManager: DataStoreManager) {

    suspend fun saveVersion(version: String) {
        dataStoreManager.saveVersion(version)
    }

    suspend fun saveSearchHistory(history: Collection<String>) {
        dataStoreManager.saveSearchHistory(history)
    }

    fun getVersion(): Flow<String> = dataStoreManager.versionFlow()

    fun getSearchHistory(): Flow<Set<String>> = dataStoreManager.searchHistoryFlow()

}