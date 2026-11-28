package com.app.max.tglab.feature.teamdetail.domain

data class Game(
    val id: Long,
    val date: String,
    val homeTeamName: String,
    val homeTeamScore: Int,
    val visitorTeamName: String,
    val visitorTeamScore: Int,
)
