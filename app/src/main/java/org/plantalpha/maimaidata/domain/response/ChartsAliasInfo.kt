package org.plantalpha.maimaidata.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class ChartsAliasInfo(
    val time: Long,
    val aliases: List<Alias>
) {

    @Serializable
    data class Alias(
        val id: Int,
        val alias: List<String>
    )
}
