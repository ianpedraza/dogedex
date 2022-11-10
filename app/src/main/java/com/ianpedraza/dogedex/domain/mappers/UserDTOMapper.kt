package com.ianpedraza.dogedex.domain.mappers

import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.framework.api.dto.UserDTO
import javax.inject.Inject

class UserDTOMapper
@Inject
constructor() : EntityMapper<UserDTO, User> {
    override fun fromResponseToDomainModel(response: UserDTO): User {
        return User(
            id = response.id,
            email = response.email,
            authenticationToken = response.authenticationToken
        )
    }

    override fun fromResponseListToDomainModelList(response: List<UserDTO>): List<User> {
        return response.map { userDTO ->
            fromResponseToDomainModel(userDTO)
        }.toList()
    }
}
