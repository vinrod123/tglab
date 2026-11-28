package com.app.max.tglab.feature.home.data

class FakeTeamsApi(
    private val response: TeamsResponseDto = TeamsResponseDto(data = emptyList()),
    private val exceptionToThrow: Throwable? = null,
) : TeamsApi {
    override suspend fun getTeams(): TeamsResponseDto {
        exceptionToThrow?.let { throw it }
        return response
    }
}
