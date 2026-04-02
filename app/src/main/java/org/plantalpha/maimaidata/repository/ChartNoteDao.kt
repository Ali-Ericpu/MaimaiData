package org.plantalpha.maimaidata.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.plantalpha.maimaidata.domain.model.MaxChartNote

@Dao
interface ChartNoteDao {

    @Query("SELECT * FROM chart WHERE version = :version")
    suspend fun getMaxChartNote(version: String): MaxChartNote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(maxChartNote: MaxChartNote)

}