package com.app.max.tglab.feature.teamdetail.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
    val id: Long,
    val date: String,
    @SerialName("home_team") val homeTeam: GameTeamDto,
    @SerialName("home_team_score") val homeTeamScore: Int? = null,
    @SerialName("visitor_team") val visitorTeam: GameTeamDto,
    @SerialName("visitor_team_score") val visitorTeamScore: Int? = null,
)
