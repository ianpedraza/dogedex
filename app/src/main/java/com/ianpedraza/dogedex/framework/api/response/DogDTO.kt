package com.ianpedraza.dogedex.framework.api.response

import com.squareup.moshi.Json

data class DogDTO(
    val id: Long,

    val index: Int,

    @field:Json(name = "created_at")
    val createdAt: String,

    @field:Json(name = "dog_type")
    val dogType: String,

    @field:Json(name = "height_female")
    val heightFemale: Double,

    @field:Json(name = "height_male")
    val heightMale: Double,

    @field:Json(name = "image_url")
    val imageUrl: String,

    @field:Json(name = "life_expectancy")
    val lifeExpectancy: String,

    @field:Json(name = "ml_id")
    val mlId: String,

    @field:Json(name = "name_en")
    val nameEn: String,

    @field:Json(name = "name_es")
    val nameEs: String,

    @field:Json(name = "temperament")
    val temperamentEs: String,

    @field:Json(name = "temperament_en")
    val temperamentEn: String,

    @field:Json(name = "updated_at")
    val updatedAt: String,

    @field:Json(name = "weight_female")
    val weightFemale: String,

    @field:Json(name = "weight_male")
    val weightMale: String
)
