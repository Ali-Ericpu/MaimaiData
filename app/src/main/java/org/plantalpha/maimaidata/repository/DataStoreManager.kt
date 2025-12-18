package org.plantalpha.maimaidata.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "song_data")

    private val versionKey = stringPreferencesKey("version")

    suspend fun saveVersion(version: String) {
        context.dataStore.updateData {
            it.toMutablePreferences().also { mutablePreferences ->
                mutablePreferences[versionKey] = version
            }
        }
    }

    fun versionFlow(): Flow<String> = context.dataStore.data.map { it[versionKey] ?: "" }

}