package com.ianpedraza.dogedex.fakes

import android.graphics.Bitmap
import com.ianpedraza.dogedex.ml.ClassifierRepository
import com.ianpedraza.dogedex.ml.DogRecognition
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeClassifierRepository @Inject constructor() : ClassifierRepository {
    private val fakeDogRecognition = DogRecognition("", 0.5f)
    private var response: DataState<DogRecognition> = DataState.Success(fakeDogRecognition)

    private fun setResponse(response: DataState<DogRecognition>) {
        this.response = response
    }

    override fun recognizeImage(bitmap: Bitmap?): Flow<DataState<DogRecognition>> = flow {
        emit(response)
    }
}
