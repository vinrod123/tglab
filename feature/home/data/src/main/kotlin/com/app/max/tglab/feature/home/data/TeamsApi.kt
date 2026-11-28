package com.app.max.tglab.feature.home.data

import retrofit2.http.GET

interface TeamsApi {
    @GET("teams")
    suspend fun getTeams(): TeamsResponseDto
}
