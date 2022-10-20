package com.ianpedraza.dogedex.data

import com.ianpedraza.dogedex.domain.models.Dog

object DummyData {
    val dogsList = mutableListOf(
        Dog(1, 1, "Chihuahua", "Toy", 5.4, 6.7, "", "12 - 15", "", "10.5 kg", "12.3 kg"),
        Dog(2, 1, "Labrador", "Toy", 5.4, 6.7, "", "12 - 15", "", "10.5 kg", "12.3 kg"),
        Dog(3, 1, "Retriever", "Toy", 5.4, 6.7, "", "12 - 15", "", "10.5 kg", "12.3 kg"),
        Dog(4, 1, "San Bernardo", "Toy", 5.4, 6.7, "", "12 - 15", "", "10.5 kg", "12.3 kg"),
        Dog(5, 1, "Husky", "Toy", 5.4, 6.7, "", "12 - 15", "", "10.5 kg", "12.3 kg"),
        Dog(6, 1, "Xoloscuincle", "Toy", 5.4, 6.7, "", "12 - 15", "", "10.5 kg", "12.3 kg")
    )
}
