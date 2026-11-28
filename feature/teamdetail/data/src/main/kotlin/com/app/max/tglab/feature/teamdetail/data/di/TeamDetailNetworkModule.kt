package com.app.max.tglab.feature.teamdetail.data.di

import com.app.max.tglab.feature.teamdetail.data.GamesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TeamDetailNetworkModule {
    @Provides
    @Singleton
    fun provideGamesApi(retrofit: Retrofit): GamesApi = retrofit.create(GamesApi::class.java)
}
