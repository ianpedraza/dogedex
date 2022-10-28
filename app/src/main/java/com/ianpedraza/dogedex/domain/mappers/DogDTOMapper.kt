package com.ianpedraza.dogedex.domain.mappers

import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.framework.api.dto.DogDTO

class DogDTOMapper : EntityMapper<DogDTO, Dog> {
    override fun fromResponseToDomainModel(response: DogDTO): Dog {
        return Dog(
            id = response.id,
            index = response.index,
            name = response.nameEs,
            type = response.dogType,
            heightFemale = response.heightFemale,
            heightMale = response.heightMale,
            imageUrl = response.imageUrl,
            lifeExpectancy = response.lifeExpectancy,
            temperament = response.temperamentEs,
            weightFemale = response.weightFemale,
            weightMale = response.weightMale
        )
    }

    override fun fromResponseListToDomainModelList(response: List<DogDTO>): List<Dog> {
        return response.map { dogDTO ->
            fromResponseToDomainModel(dogDTO)
        }
    }
}
