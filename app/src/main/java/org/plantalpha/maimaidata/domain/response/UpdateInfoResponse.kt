package org.plantalpha.maimaidata.domain.response

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateInfoResponse(
    /**
     * @param version apk version string
     */
    @JsonProperty("apk_version")
    var version: String,

    /**
     * @param url newest apk url
     */
    @JsonProperty("apk_url")
    var url: String,

    /**
     * @param info update info
     */
    @JsonProperty("apk_info")
    var info: String? = null,

    /**
     * @param dataVersion dx2025 json data version string
     */
    @JsonProperty("data_version")
    var dataVersion: String,

    /**
     * @param dataUrl dx2025 json url
     */
    @JsonProperty("data_url")
    var dataUrl: String
)