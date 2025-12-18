package org.plantalpha.maimaidata.domain.model

import kotlinx.serialization.SerialName

data class ChartStat(
    val id: Int,
    @SerialName("fit_difficulty")
    val fitDifficulty: List<Double>
)