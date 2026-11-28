package com.app.max.tglab.feature.teamdetail.presentation

import kotlinx.serialization.Serializable

@Serializable
data class TeamDetailRoute(
    val teamId: Long,
    val teamName: String,
)
