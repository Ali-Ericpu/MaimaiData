package org.plantalpha.maimaidata.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import org.plantalpha.maimaidata.domain.model.Song

@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract class SongDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao
}
