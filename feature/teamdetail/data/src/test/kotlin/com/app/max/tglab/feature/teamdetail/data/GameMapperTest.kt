package com.app.max.tglab.feature.teamdetail.data

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GameMapperTest {
    @Test
    fun `should map homeTeamName, homeTeamScore, visitorTeamName and visitorTeamScore from dto to domain when both scores are present (AC-2)`() {
        val gameDto =
            GameDto(
                id = 1,
                date = "2026-03-01",
                homeTeam = GameTeamDto(fullName = "Atlanta Hawks"),
                homeTeamScore = 102,
                visitorTeam = GameTeamDto(fullName = "Boston Celtics"),
                visitorTeamScore = 98,
            )

        val game = gameDto.toDomain()

        assertThat(game).isNotNull()
        assertThat(game?.homeTeamName).isEqualTo("Atlanta Hawks")
        assertThat(game?.homeTeamScore).isEqualTo(102)
        assertThat(game?.visitorTeamName).isEqualTo("Boston Celtics")
        assertThat(game?.visitorTeamScore).isEqualTo(98)
    }

    @Test
    fun `should return null when homeTeamScore is null (AC-2)`() {
        val gameDto =
            GameDto(
                id = 1,
                date = "2026-03-01",
                homeTeam = GameTeamDto(fullName = "Atlanta Hawks"),
                homeTeamScore = null,
                visitorTeam = GameTeamDto(fullName = "Boston Celtics"),
                visitorTeamScore = 98,
            )

        val game = gameDto.toDomain()

        assertThat(game).isNull()
    }

    @Test
    fun `should return null when visitorTeamScore is null (AC-2)`() {
        val gameDto =
            GameDto(
                id = 1,
                date = "2026-03-01",
                homeTeam = GameTeamDto(fullName = "Atlanta Hawks"),
                homeTeamScore = 102,
                visitorTeam = GameTeamDto(fullName = "Boston Celtics"),
                visitorTeamScore = null,
            )

        val game = gameDto.toDomain()

        assertThat(game).isNull()
    }
}
