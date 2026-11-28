package com.app.max.tglab.feature.home.presentation

import com.app.max.tglab.core.common.DomainError
import com.app.max.tglab.feature.home.domain.Team

fun Team.toUiModel(): TeamUiModel =
    TeamUiModel(
        id = id,
        fullName = fullName,
        city = city,
        conference = conference,
    )

fun DomainError.toHomeErrorReason(): HomeErrorReason =
    when (this) {
        is DomainError.NetworkUnavailable -> HomeErrorReason.NETWORK_UNAVAILABLE
        is DomainError.Http -> HomeErrorReason.SERVER
        is DomainError.Unknown -> HomeErrorReason.UNKNOWN
    }
