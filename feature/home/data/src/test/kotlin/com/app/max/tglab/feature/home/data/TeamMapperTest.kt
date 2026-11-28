package com.app.max.tglab.feature.home.data

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TeamMapperTest {
    @Test
    fun `should map id, fullName, city and conference from dto to domain (AC-1)`() {
        val teamDto =
            TeamDto(
                id = 1,
                fullName = "Atlanta Hawks",
                city = "Atlanta",
                conference = "East",
            )

        val team = teamDto.toDomain()

        assertThat(team.id).isEqualTo(1L)
        assertThat(team.fullName).isEqualTo("Atlanta Hawks")
        assertThat(team.city).isEqualTo("Atlanta")
        assertThat(team.conference).isEqualTo("East")
    }
}
