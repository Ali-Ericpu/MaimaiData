package org.plantalpha.maimaidata.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import org.plantalpha.maimaidata.domain.model.MaxChartNote
import org.plantalpha.maimaidata.domain.model.Song

@Database(entities = [Song::class, MaxChartNote::class], version = 2, exportSchema = false)
abstract class SongDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao

    abstract fun chartNoteDao(): ChartNoteDao

}
