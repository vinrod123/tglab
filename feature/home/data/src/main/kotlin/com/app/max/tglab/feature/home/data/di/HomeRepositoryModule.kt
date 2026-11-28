package com.app.max.tglab.feature.home.data.di

import com.app.max.tglab.core.common.DefaultDispatcherProvider
import com.app.max.tglab.core.common.DispatcherProvider
import com.app.max.tglab.feature.home.data.TeamsRepositoryImpl
import com.app.max.tglab.feature.home.domain.GetTeamsUseCase
import com.app.max.tglab.feature.home.domain.TeamsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeRepositoryModule {
    @Binds
    abstract fun bindTeamsRepository(teamsRepositoryImpl: TeamsRepositoryImpl): TeamsRepository

    companion object {
        @Provides
        @Singleton
        fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

        @Provides
        fun provideGetTeamsUseCase(teamsRepository: TeamsRepository): GetTeamsUseCase = GetTeamsUseCase(teamsRepository)
    }
}
