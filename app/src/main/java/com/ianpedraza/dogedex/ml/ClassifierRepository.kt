package com.ianpedraza.dogedex.ml

import android.graphics.Bitmap
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow

interface ClassifierRepository {
    fun recognizeImage(bitmap: Bitmap?): Flow<DataState<DogRecognition>>
}
