package com.app.max.tglab.feature.teamdetail.data

import com.app.max.tglab.feature.teamdetail.domain.Game

fun GameDto.toDomain(): Game? {
    val completedHomeTeamScore = homeTeamScore
    val completedVisitorTeamScore = visitorTeamScore
    if (completedHomeTeamScore == null || completedVisitorTeamScore == null) return null
    return Game(
        id = id,
        date = date,
        homeTeamName = homeTeam.fullName,
        homeTeamScore = completedHomeTeamScore,
        visitorTeamName = visitorTeam.fullName,
        visitorTeamScore = completedVisitorTeamScore,
    )
}
