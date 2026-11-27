package com.app.max.tglab.core.common

sealed interface DomainResult<out T> {
    data class Success<out T>(
        val data: T,
    ) : DomainResult<T>

    data class Error(
        val error: DomainError,
    ) : DomainResult<Nothing>
}
