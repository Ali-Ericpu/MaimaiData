package org.plantalpha.maimaidata.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.plantalpha.maimaidata.domain.model.Song

@Dao
interface SongDao {

    @Query("SELECT * FROM song")
    suspend fun getAll(): List<Song>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(songList: List<Song>)

    @Query("DELETE FROM song")
    suspend fun deleteAll()

}