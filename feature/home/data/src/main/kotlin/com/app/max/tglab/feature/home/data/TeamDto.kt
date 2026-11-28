package com.app.max.tglab.feature.home.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamDto(
    val id: Long,
    @SerialName("full_name") val fullName: String,
    val city: String,
    val conference: String,
)
