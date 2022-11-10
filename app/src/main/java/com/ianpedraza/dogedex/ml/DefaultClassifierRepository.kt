package com.ianpedraza.dogedex.ml

import android.graphics.Bitmap
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultClassifierRepository
@Inject
constructor(
    private val classifier: Classifier
) : ClassifierRepository {
    override fun recognizeImage(bitmap: Bitmap?): Flow<DataState<DogRecognition>> = flow {
        emit(DataState.Loading)

        if (bitmap != null) {
            val dogRecognition = classifier.recognizeImage(bitmap).first()
            emit(DataState.Success(dogRecognition))
        } else {
            emit(DataState.Error(R.string.error_invalid_bitmap))
        }
    }
}
