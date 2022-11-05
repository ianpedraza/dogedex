package com.ianpedraza.dogedex.usecases

import android.graphics.Bitmap
import com.ianpedraza.dogedex.ml.ClassifierRepository

class RecognizeImageUseCase(private val repository: ClassifierRepository) {
    operator fun invoke(bitmap: Bitmap?) = repository.recognizeImage(bitmap)
}
