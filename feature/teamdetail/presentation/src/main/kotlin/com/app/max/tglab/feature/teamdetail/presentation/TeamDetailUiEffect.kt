package com.app.max.tglab.feature.teamdetail.presentation

import com.app.max.tglab.core.ui.MviViewModel

sealed interface TeamDetailUiEffect : MviViewModel.UiEffect {
    data object NavigateBack : TeamDetailUiEffect
}
