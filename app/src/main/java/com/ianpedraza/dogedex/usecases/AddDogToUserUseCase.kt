package com.ianpedraza.dogedex.usecases

import com.ianpedraza.dogedex.data.repository.user.UserRepository
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddDogToUserUseCase
@Inject
constructor(
    private val repository: UserRepository
) {
    operator fun invoke(dogId: Long): Flow<DataState<Boolean>> =
        repository.addDogToUser(dogId)
}
