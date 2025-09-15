package org.plantalpha.maimaidata.domain.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ChartsResponse(val charts: Map<String, List<ChartData>>)

data class ChartData(
    val cnt: Double?,
    val diff: String?,
    @JsonProperty("fit_diff")
    val fitDiff: Double?,
    val avg: Double?,
    @JsonProperty("avg_dx")
    val avgDx: Double?,
    @JsonProperty("std_dev")
    val stdDev: Double?,
    val dist: List<Int>?,
    @JsonProperty("fc_dist")
    val fcDist: List<Int>?
)