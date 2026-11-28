package com.app.max.tglab.feature.teamdetail.data.di

import com.app.max.tglab.feature.teamdetail.data.GamesRepositoryImpl
import com.app.max.tglab.feature.teamdetail.domain.GamesRepository
import com.app.max.tglab.feature.teamdetail.domain.GetTeamGamesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TeamDetailRepositoryModule {
    @Binds
    abstract fun bindGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository

    companion object {
        @Provides
        fun provideGetTeamGamesUseCase(gamesRepository: GamesRepository): GetTeamGamesUseCase =
            GetTeamGamesUseCase(gamesRepository)
    }
}
