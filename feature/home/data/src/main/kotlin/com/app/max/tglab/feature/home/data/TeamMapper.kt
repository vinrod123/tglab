package com.app.max.tglab.feature.home.data

import com.app.max.tglab.feature.home.domain.Team

fun TeamDto.toDomain(): Team =
    Team(
        id = id,
        fullName = fullName,
        city = city,
        conference = conference,
    )
