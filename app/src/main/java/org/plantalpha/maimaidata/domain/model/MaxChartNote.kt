package org.plantalpha.maimaidata.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "chart")
@Serializable
data class MaxChartNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(index = true)
    val version: String = "",
    val maxTap: Int = 1000,
    val maxHold: Int = 1000,
    val maxSlide: Int = 1000,
    val maxTouch: Int = 1000,
    val maxBreak: Int = 1000,
    val maxTotal: Int = 1000
) {

    fun entries(): Map<String, Int> = mapOf(
        "Total" to maxTotal,
        "Tab" to maxTap,
        "Hold" to maxHold,
        "Slide" to maxSlide,
        "Touch" to maxTouch,
        "Break" to maxBreak
    )

}

