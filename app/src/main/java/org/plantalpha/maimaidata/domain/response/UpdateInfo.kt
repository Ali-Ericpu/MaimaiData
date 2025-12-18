package org.plantalpha.maimaidata.domain.response

import kotlinx.serialization.SerialName

data class UpdateInfo(
    /**
     * @param version apk version string
     */
    @SerialName("apk_version")
    var version: String,

    /**
     * @param url newest apk url
     */
    @SerialName("apk_url")
    var url: String,

    /**
     * @param info update info
     */
    @SerialName("apk_info")
    var info: String? = null,

    /**
     * @param dataVersion dx2025 json data version string
     */
    @SerialName("data_version")
    var dataVersion: String,

    /**
     * @param dataUrl dx2025 json url
     */
    @SerialName("data_url")
    var dataUrl: String
)