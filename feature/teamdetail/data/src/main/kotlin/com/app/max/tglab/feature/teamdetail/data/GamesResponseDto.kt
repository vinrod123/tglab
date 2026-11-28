package com.app.max.tglab.feature.teamdetail.data

import kotlinx.serialization.Serializable

@Serializable
data class GamesResponseDto(
    val data: List<GameDto>,
)
