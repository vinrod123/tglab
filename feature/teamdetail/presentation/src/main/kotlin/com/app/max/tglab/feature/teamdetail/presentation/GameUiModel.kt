package com.app.max.tglab.feature.teamdetail.presentation

data class GameUiModel(
    val id: Long,
    val homeTeamName: String,
    val homeTeamScore: Int,
    val visitorTeamName: String,
    val visitorTeamScore: Int,
)
