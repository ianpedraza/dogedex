package com.ianpedraza.dogedex.framework.api.dogs.response

import com.ianpedraza.dogedex.framework.api.dto.DogDTO

data class DogsListDataResponse(
    val dogs: List<DogDTO>
)
