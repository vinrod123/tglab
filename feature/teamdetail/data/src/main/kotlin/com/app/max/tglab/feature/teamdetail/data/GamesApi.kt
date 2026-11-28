package com.app.max.tglab.feature.teamdetail.data

import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApi {
    @GET("games")
    suspend fun getGames(
        @Query("team_ids[]") teamIds: List<Long>,
    ): GamesResponseDto
}
