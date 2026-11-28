package com.app.max.tglab.feature.teamdetail.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.app.max.tglab.core.common.DomainError
import com.app.max.tglab.core.common.DomainResult
import com.app.max.tglab.core.testing.MainDispatcherRule
import com.app.max.tglab.feature.teamdetail.domain.Game
import com.app.max.tglab.feature.teamdetail.domain.GamesRepository
import com.app.max.tglab.feature.teamdetail.domain.GetTeamGamesUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class TeamDetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class FakeGamesRepository : GamesRepository {
        var result: DomainResult<List<Game>> = DomainResult.Success(emptyList())
        var getTeamGamesCallCount = 0
        var lastTeamIdRequested: Long? = null

        override suspend fun getTeamGames(teamId: Long): DomainResult<List<Game>> {
            getTeamGamesCallCount++
            lastTeamIdRequested = teamId
            return result
        }
    }

    private class SuspendingFakeGamesRepository : GamesRepository {
        var wasCalled = false
        private val resultDeferred = CompletableDeferred<DomainResult<List<Game>>>()

        override suspend fun getTeamGames(teamId: Long): DomainResult<List<Game>> {
            wasCalled = true
            return resultDeferred.await()
        }
    }

    private fun savedStateHandleFor(
        teamId: Long,
        teamName: String,
    ): SavedStateHandle = SavedStateHandle(mapOf("teamId" to teamId, "teamName" to teamName))

    @Test
    fun `should emit Content state with games mapped from domain, in the order received, when LoadGames succeeds with a non-empty list (AC-2)`() =
        runTest {
            val fakeGamesRepository =
                FakeGamesRepository().apply {
                    result =
                        DomainResult.Success(
                            listOf(
                                Game(
                                    id = 1,
                                    date = "2026-03-01",
                                    homeTeamName = "Atlanta Hawks",
                                    homeTeamScore = 102,
                                    visitorTeamName = "Boston Celtics",
                                    visitorTeamScore = 98,
                                ),
                            ),
                        )
                }
            val teamDetailViewModel =
                TeamDetailViewModel(savedStateHandleFor(teamId = 1, teamName = "Atlanta Hawks"), GetTeamGamesUseCase(fakeGamesRepository))

            teamDetailViewModel.state.test {
                assertThat(awaitItem()).isEqualTo(TeamDetailUiState.Loading)

                teamDetailViewModel.onIntent(TeamDetailUiIntent.LoadGames)

                val contentState = awaitItem()
                assertThat(contentState).isInstanceOf(TeamDetailUiState.Content::class.java)
                assertThat((contentState as TeamDetailUiState.Content).games.map { it.id })
                    .containsExactly(1L)
            }
        }

    @Test
    fun `should emit NavigateBack effect when NavigateBack intent is dispatched (AC-3)`() =
        runTest {
            val fakeGamesRepository = FakeGamesRepository()
            val teamDetailViewModel =
                TeamDetailViewModel(savedStateHandleFor(teamId = 1, teamName = "Atlanta Hawks"), GetTeamGamesUseCase(fakeGamesRepository))

            teamDetailViewModel.effect.test {
                teamDetailViewModel.onIntent(TeamDetailUiIntent.NavigateBack)

                assertThat(awaitItem()).isEqualTo(TeamDetailUiEffect.NavigateBack)
            }
        }

    @Test
    fun `should emit Empty state when GetTeamGamesUseCase succeeds with an empty list (AC-4)`() =
        runTest {
            val fakeGamesRepository =
                FakeGamesRepository().apply {
                    result = DomainResult.Success(emptyList())
                }
            val teamDetailViewModel =
                TeamDetailViewModel(savedStateHandleFor(teamId = 1, teamName = "Atlanta Hawks"), GetTeamGamesUseCase(fakeGamesRepository))

            teamDetailViewModel.state.test {
                assertThat(awaitItem()).isEqualTo(TeamDetailUiState.Loading)

                teamDetailViewModel.onIntent(TeamDetailUiIntent.LoadGames)

                assertThat(awaitItem()).isEqualTo(TeamDetailUiState.Empty)
            }
        }

    @Test
    fun `should emit Loading state immediately when LoadGames is dispatched, before the repository result arrives (AC-5)`() =
        runTest {
            val suspendingFakeGamesRepository = SuspendingFakeGamesRepository()
            val teamDetailViewModel =
                TeamDetailViewModel(
                    savedStateHandleFor(teamId = 1, teamName = "Atlanta Hawks"),
                    GetTeamGamesUseCase(suspendingFakeGamesRepository),
                )

            teamDetailViewModel.onIntent(TeamDetailUiIntent.LoadGames)

            assertThat(suspendingFakeGamesRepository.wasCalled).isTrue()
            assertThat(teamDetailViewModel.state.value).isEqualTo(TeamDetailUiState.Loading)
        }

    @Test
    fun `should emit Error state with reason SERVER when GetTeamGamesUseCase returns DomainError Http (AC-6)`() =
        runTest {
            val fakeGamesRepository =
                FakeGamesRepository().apply {
                    result = DomainResult.Error(DomainError.Http(500))
                }
            val teamDetailViewModel =
                TeamDetailViewModel(savedStateHandleFor(teamId = 1, teamName = "Atlanta Hawks"), GetTeamGamesUseCase(fakeGamesRepository))

            teamDetailViewModel.state.test {
                assertThat(awaitItem()).isEqualTo(TeamDetailUiState.Loading)

                teamDetailViewModel.onIntent(TeamDetailUiIntent.LoadGames)

                assertThat(awaitItem()).isEqualTo(TeamDetailUiState.Error(TeamDetailErrorReason.SERVER))
            }
        }

    @Test
    fun `should emit Error state with reason UNKNOWN when GetTeamGamesUseCase returns DomainError Unknown (AC-6)`() =
        runTest {
            val fakeGamesRepository =
                FakeGamesRepository().apply {
                    result = DomainResult.Error(DomainError.Unknown)
                }
            val teamDetailViewModel =
                TeamDetailViewModel(savedStateHandleFor(teamId = 1, teamName = "Atlanta Hawks"), GetTeamGamesUseCase(fakeGamesRepository))

            teamDetailViewModel.state.test {
                assertThat(awaitItem()).isEqualTo(TeamDetailUiState.Loading)

                teamDetailViewModel.onIntent(TeamDetailUiIntent.LoadGames)

                assertThat(awaitItem()).isEqualTo(TeamDetailUiState.Error(TeamDetailErrorReason.UNKNOWN))
            }
        }

    @Test
    fun `should emit Error state with reason NETWORK_UNAVAILABLE when GetTeamGamesUseCase returns DomainError NetworkUnavailable (AC-6)`() =
        runTest {
            val fakeGamesRepository =
                FakeGamesRepository().apply {
                    result = DomainResult.Error(DomainError.NetworkUnavailable)
                }
            val teamDetailViewModel =
                TeamDetailViewModel(savedStateHandleFor(teamId = 1, teamName = "Atlanta Hawks"), GetTeamGamesUseCase(fakeGamesRepository))

            teamDetailViewModel.state.test {
                assertThat(awaitItem()).isEqualTo(TeamDetailUiState.Loading)

                teamDetailViewModel.onIntent(TeamDetailUiIntent.LoadGames)

                assertThat(awaitItem()).isEqualTo(TeamDetailUiState.Error(TeamDetailErrorReason.NETWORK_UNAVAILABLE))
            }
        }

    @Test
    fun `should call GetTeamGamesUseCase again, for the same teamId, when LoadGames is dispatched after an Error state (AC-6)`() =
        runTest {
            val fakeGamesRepository =
                FakeGamesRepository().apply {
                    result = DomainResult.Error(DomainError.Http(500))
                }
            val teamDetailViewModel =
                TeamDetailViewModel(savedStateHandleFor(teamId = 7, teamName = "Atlanta Hawks"), GetTeamGamesUseCase(fakeGamesRepository))

            teamDetailViewModel.onIntent(TeamDetailUiIntent.LoadGames)
            teamDetailViewModel.onIntent(TeamDetailUiIntent.LoadGames)

            assertThat(fakeGamesRepository.getTeamGamesCallCount).isEqualTo(2)
            assertThat(fakeGamesRepository.lastTeamIdRequested).isEqualTo(7L)
        }

    @Test
    fun `should expose teamName read from the SavedStateHandle when constructed (AC-7)`() =
        runTest {
            val fakeGamesRepository = FakeGamesRepository()
            val teamDetailViewModel =
                TeamDetailViewModel(
                    savedStateHandleFor(teamId = 1, teamName = "Atlanta Hawks"),
                    GetTeamGamesUseCase(fakeGamesRepository),
                )

            assertThat(teamDetailViewModel.teamName).isEqualTo("Atlanta Hawks")
        }

    @Test
    fun `should call GetTeamGamesUseCase with the teamId read from the SavedStateHandle when LoadGames is dispatched (AC-7)`() =
        runTest {
            val fakeGamesRepository = FakeGamesRepository()
            val teamDetailViewModel =
                TeamDetailViewModel(savedStateHandleFor(teamId = 42, teamName = "Atlanta Hawks"), GetTeamGamesUseCase(fakeGamesRepository))

            teamDetailViewModel.onIntent(TeamDetailUiIntent.LoadGames)

            assertThat(fakeGamesRepository.lastTeamIdRequested).isEqualTo(42L)
        }
}
