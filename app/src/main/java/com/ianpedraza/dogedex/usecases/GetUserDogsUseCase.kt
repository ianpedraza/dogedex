package com.ianpedraza.dogedex.usecases

import com.ianpedraza.dogedex.data.repository.user.UserRepository
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow

class GetUserDogsUseCase(private val repository: UserRepository) {
    operator fun invoke(): Flow<DataState<List<Dog>>> = repository.getUserDogs()
}
