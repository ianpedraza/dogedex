package com.ianpedraza.dogedex.data.repository.user

import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.data.datasource.UserDataSource
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.DogCollectionUtils
import com.ianpedraza.dogedex.utils.NetworkUtils.Companion.makeNetworkCall
import kotlinx.coroutines.flow.Flow

class DefaultUserRepository(
    private val userDataSource: UserDataSource,
    private val dogsDataSource: DogsDataSource
) : UserRepository {
    override suspend fun addDogToUser(dogId: Long): Flow<DataState<Boolean>> =
        makeNetworkCall { userDataSource.addDogToUser(dogId) }

    override suspend fun getUserDogs(): Flow<DataState<List<Dog>>> =
        makeNetworkCall { userDataSource.getUserDogs() }

    override suspend fun getDogsCollection(): Flow<DataState<List<Dog>>> {
        return makeNetworkCall {
            val allDogs = dogsDataSource.getAll()
            val userDogs = userDataSource.getUserDogs()
            DogCollectionUtils.create(allDogs, userDogs)
        }
    }
}
