package com.app.max.tglab.feature.teamdetail.presentation

import com.app.max.tglab.core.common.DomainError
import com.app.max.tglab.feature.teamdetail.domain.Game

fun Game.toUiModel(): GameUiModel =
    GameUiModel(
        id = id,
        homeTeamName = homeTeamName,
        homeTeamScore = homeTeamScore,
        visitorTeamName = visitorTeamName,
        visitorTeamScore = visitorTeamScore,
    )

fun DomainError.toTeamDetailErrorReason(): TeamDetailErrorReason =
    when (this) {
        is DomainError.NetworkUnavailable -> TeamDetailErrorReason.NETWORK_UNAVAILABLE
        is DomainError.Http -> TeamDetailErrorReason.SERVER
        is DomainError.Unknown -> TeamDetailErrorReason.UNKNOWN
    }
