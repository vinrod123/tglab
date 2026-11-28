package com.app.max.tglab.feature.teamdetail.data

class FakeGamesApi(
    private val response: GamesResponseDto = GamesResponseDto(data = emptyList()),
    private val exceptionToThrow: Throwable? = null,
) : GamesApi {
    override suspend fun getGames(teamIds: List<Long>): GamesResponseDto {
        exceptionToThrow?.let { throw it }
        return response
    }
}
