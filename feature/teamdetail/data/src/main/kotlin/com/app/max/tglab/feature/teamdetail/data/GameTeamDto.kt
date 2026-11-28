package com.app.max.tglab.feature.teamdetail.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameTeamDto(
    @SerialName("full_name") val fullName: String,
)
