package com.ianpedraza.dogedex.utils

import com.ianpedraza.dogedex.domain.models.Dog

class DogCollectionUtils {
    companion object {
        fun create(allDogs: List<Dog>, userDogs: List<Dog>): List<Dog> {
            return allDogs.map { dog ->
                if (userDogs.contains(dog)) {
                    dog
                } else {
                    Dog(id = dog.id, index = dog.index, inCollection = false)
                }
            }.sortedBy { dog -> dog.index }
        }
    }
}
