package com.app.max.tglab.feature.home.data

import kotlinx.serialization.Serializable

@Serializable
data class TeamsResponseDto(
    val data: List<TeamDto>,
)
