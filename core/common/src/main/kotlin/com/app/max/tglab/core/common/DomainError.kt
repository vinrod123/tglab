package com.app.max.tglab.core.common

sealed interface DomainError {
    data object NetworkUnavailable : DomainError

    data class Http(
        val code: Int,
    ) : DomainError

    data object Unknown : DomainError
}
