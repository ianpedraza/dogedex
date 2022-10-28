package com.ianpedraza.dogedex.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

private const val EMPTY_STRING = ""

@Parcelize
data class Dog(
    val id: Long = -1L,
    val index: Int = -1,
    val name: String = EMPTY_STRING,
    val type: String = EMPTY_STRING,
    val heightFemale: Double = 0.0,
    val heightMale: Double = 0.0,
    val imageUrl: String = EMPTY_STRING,
    val lifeExpectancy: String = EMPTY_STRING,
    val temperament: String = EMPTY_STRING,
    val weightFemale: String = EMPTY_STRING,
    val weightMale: String = EMPTY_STRING,
    val inCollection: Boolean = true
) : Parcelable
