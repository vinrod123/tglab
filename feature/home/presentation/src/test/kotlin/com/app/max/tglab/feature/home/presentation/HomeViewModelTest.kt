package com.app.max.tglab.feature.home.presentation

import app.cash.turbine.test
import com.app.max.tglab.core.common.DomainError
import com.app.max.tglab.core.common.DomainResult
import com.app.max.tglab.core.testing.MainDispatcherRule
import com.app.max.tglab.feature.home.domain.GetTeamsUseCase
import com.app.max.tglab.feature.home.domain.Team
import com.app.max.tglab.feature.home.domain.TeamsRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class FakeTeamsRepository : TeamsRepository {
        var result: DomainResult<List<Team>> = DomainResult.Success(emptyList())
        var getTeamsCallCount = 0

        override suspend fun getTeams(): DomainResult<List<Team>> {
            getTeamsCallCount++
            return result
        }
    }

    private class SuspendingFakeTeamsRepository : TeamsRepository {
        var wasCalled = false
        private val resultDeferred = CompletableDeferred<DomainResult<List<Team>>>()

        override suspend fun getTeams(): DomainResult<List<Team>> {
            wasCalled = true
            return resultDeferred.await()
        }
    }

    @Test
    fun `should emit Content state with teams sorted by name when LoadTeams succeeds with a non-empty list (AC-1)`() =
        runTest {
            val fakeTeamsRepository =
                FakeTeamsRepository().apply {
                    result =
                        DomainResult.Success(
                            listOf(Team(id = 1, fullName = "Atlanta Hawks", city = "Atlanta", conference = "East")),
                        )
                }
            val homeViewModel = HomeViewModel(GetTeamsUseCase(fakeTeamsRepository))

            homeViewModel.state.test {
                assertThat(awaitItem()).isEqualTo(HomeUiState.Loading)

                homeViewModel.onIntent(HomeUiIntent.LoadTeams)

                val contentState = awaitItem()
                assertThat(contentState).isInstanceOf(HomeUiState.Content::class.java)
                assertThat((contentState as HomeUiState.Content).teams.map { it.fullName })
                    .containsExactly("Atlanta Hawks")
            }
        }

    @Test
    fun `should emit Loading state immediately when LoadTeams is dispatched before the repository result arrives (AC-2)`() =
        runTest {
            val suspendingFakeTeamsRepository = SuspendingFakeTeamsRepository()
            val homeViewModel = HomeViewModel(GetTeamsUseCase(suspendingFakeTeamsRepository))

            homeViewModel.onIntent(HomeUiIntent.LoadTeams)

            assertThat(suspendingFakeTeamsRepository.wasCalled).isTrue()
            assertThat(homeViewModel.state.value).isEqualTo(HomeUiState.Loading)
        }

    @Test
    fun `should emit Error state with reason SERVER when GetTeamsUseCase returns DomainError Http (AC-3)`() =
        runTest {
            val fakeTeamsRepository =
                FakeTeamsRepository().apply {
                    result = DomainResult.Error(DomainError.Http(500))
                }
            val homeViewModel = HomeViewModel(GetTeamsUseCase(fakeTeamsRepository))

            homeViewModel.state.test {
                assertThat(awaitItem()).isEqualTo(HomeUiState.Loading)

                homeViewModel.onIntent(HomeUiIntent.LoadTeams)

                assertThat(awaitItem()).isEqualTo(HomeUiState.Error(HomeErrorReason.SERVER))
            }
        }

    @Test
    fun `should emit Error state with reason UNKNOWN when GetTeamsUseCase returns DomainError Unknown (AC-3)`() =
        runTest {
            val fakeTeamsRepository =
                FakeTeamsRepository().apply {
                    result = DomainResult.Error(DomainError.Unknown)
                }
            val homeViewModel = HomeViewModel(GetTeamsUseCase(fakeTeamsRepository))

            homeViewModel.state.test {
                assertThat(awaitItem()).isEqualTo(HomeUiState.Loading)

                homeViewModel.onIntent(HomeUiIntent.LoadTeams)

                assertThat(awaitItem()).isEqualTo(HomeUiState.Error(HomeErrorReason.UNKNOWN))
            }
        }

    @Test
    fun `should call GetTeamsUseCase again when LoadTeams is dispatched after an Error state (AC-3)`() =
        runTest {
            val fakeTeamsRepository =
                FakeTeamsRepository().apply {
                    result = DomainResult.Error(DomainError.Http(500))
                }
            val homeViewModel = HomeViewModel(GetTeamsUseCase(fakeTeamsRepository))

            homeViewModel.onIntent(HomeUiIntent.LoadTeams)
            homeViewModel.onIntent(HomeUiIntent.LoadTeams)

            assertThat(fakeTeamsRepository.getTeamsCallCount).isEqualTo(2)
        }

    @Test
    fun `should emit Empty state when GetTeamsUseCase succeeds with an empty list (AC-4)`() =
        runTest {
            val fakeTeamsRepository =
                FakeTeamsRepository().apply {
                    result = DomainResult.Success(emptyList())
                }
            val homeViewModel = HomeViewModel(GetTeamsUseCase(fakeTeamsRepository))

            homeViewModel.state.test {
                assertThat(awaitItem()).isEqualTo(HomeUiState.Loading)

                homeViewModel.onIntent(HomeUiIntent.LoadTeams)

                assertThat(awaitItem()).isEqualTo(HomeUiState.Empty)
            }
        }

    @Test
    fun `should emit Error state with reason NETWORK_UNAVAILABLE when GetTeamsUseCase returns DomainError NetworkUnavailable (AC-5)`() =
        runTest {
            val fakeTeamsRepository =
                FakeTeamsRepository().apply {
                    result = DomainResult.Error(DomainError.NetworkUnavailable)
                }
            val homeViewModel = HomeViewModel(GetTeamsUseCase(fakeTeamsRepository))

            homeViewModel.state.test {
                assertThat(awaitItem()).isEqualTo(HomeUiState.Loading)

                homeViewModel.onIntent(HomeUiIntent.LoadTeams)

                assertThat(awaitItem()).isEqualTo(HomeUiState.Error(HomeErrorReason.NETWORK_UNAVAILABLE))
            }
        }
}
