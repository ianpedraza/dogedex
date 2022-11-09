package com.ianpedraza.dogedex.framework.dummy.dogs

import com.ianpedraza.dogedex.domain.models.Dog

object DummyData {
    private val dogsList = mutableListOf(
        Dog(1, 1, "Chihuahua", "Toy", 5.4, 6.7, "https://www.purina.es/sites/default/files/styles/ttt_image_original/public/2021-02/BREED%20Hero%20Desktop_0139_chihuahua_smooth.webp", "12 - 15", "", "10.5 kg", "12.3 kg"),
        Dog(2, 1, "Labrador", "Toy", 5.4, 6.7, "", "12 - 15", "", "10.5 kg", "12.3 kg"),
        Dog(3, 1, "Retriever", "Toy", 5.4, 6.7, "", "12 - 15", "", "10.5 kg", "12.3 kg"),
        Dog(4, 1, "San Bernardo", "Toy", 5.4, 6.7, "", "12 - 15", "", "10.5 kg", "12.3 kg"),
        Dog(5, 1, "Husky", "Toy", 5.4, 6.7, "", "12 - 15", "", "10.5 kg", "12.3 kg"),
        Dog(6, 1, "Xoloscuincle", "Toy", 5.4, 6.7, "", "12 - 15", "", "10.5 kg", "12.3 kg")
    )

    private val userDogs = mutableListOf<Dog>()

    fun getAllDogs(): List<Dog> = dogsList

    fun addDogToUser(dogId: Long): Boolean {
        val dog = dogsList.firstOrNull { dog -> dog.id == dogId } ?: return false
        return userDogs.add(dog)
    }
}
