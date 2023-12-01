package features.common.domain

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<T> {
    suspend fun execute() : Flow<Result<T>>
}