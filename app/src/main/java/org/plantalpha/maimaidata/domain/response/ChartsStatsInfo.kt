package org.plantalpha.maimaidata.domain.response

import org.plantalpha.maimaidata.domain.model.ChartStat

data class ChartsStatsInfo(
    val time: Long,
    val charts: List<ChartStat>
)