package com.ianpedraza.dogedex.ui.home

import android.graphics.Bitmap
import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.ml.DogRecognition
import com.ianpedraza.dogedex.usecases.GetDogByMlIdUseCase
import com.ianpedraza.dogedex.usecases.RecognizeImageUseCase
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.ImageProxyExtensions.Companion.toBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val getDogByMlIdUseCase: GetDogByMlIdUseCase,
    private val recognizeImageUseCase: RecognizeImageUseCase
) : ViewModel() {
    private val _isCameraReady = MutableLiveData<Boolean>()
    val isCameraReady: LiveData<Boolean> get() = _isCameraReady

    private val _takenPhoto = MutableLiveData<Uri?>()
    val takenPhoto: LiveData<Uri?> get() = _takenPhoto

    private val _dogRecognized = MutableLiveData<DataState<Dog>?>()
    val dogRecognized: LiveData<DataState<Dog>?> get() = _dogRecognized

    private val _imageRecognizedRT = MutableLiveData<DataState<DogRecognition>?>()
    val imageRecognizedRT: LiveData<DataState<DogRecognition>?> get() = _imageRecognizedRT

    private val _imageRecognized = MutableLiveData<DataState<DogRecognition>?>()
    val imageRecognized: LiveData<DataState<DogRecognition>?> get() = _imageRecognized

    fun setCameraReady() {
        _isCameraReady.value = true
    }

    fun setTakenPhoto(uri: Uri?) {
        viewModelScope.launch {
            _takenPhoto.value = uri
        }
    }

    fun handledTakenPhoto() {
        _takenPhoto.value = null
    }

    fun handledDogDetail() {
        _dogRecognized.value = null
    }

    fun handledImageRecognized() {
        _imageRecognized.value = null
    }

    fun handledImageRecognizedRT() {
        _imageRecognizedRT.value = null
    }

    fun getDogByMlId(mlDogId: String) {
        viewModelScope.launch {
            getDogByMlIdUseCase(mlDogId).onEach { dataState ->
                _dogRecognized.value = dataState
            }.launchIn(viewModelScope)
        }
    }

    fun recognizeImageRT(imageProxy: ImageProxy) {
        viewModelScope.launch {
            val bitmap = imageProxy.toBitmap()
            imageProxy.close()

            recognizeImageUseCase(bitmap).onEach { dataState ->
                _imageRecognizedRT.value = dataState
            }.launchIn(viewModelScope)
        }
    }

    fun recognizeImageRT(bitmap: Bitmap) {
        viewModelScope.launch {
            recognizeImageUseCase(bitmap).onEach { dataState ->
                _imageRecognized.value = dataState
            }.launchIn(viewModelScope)
        }
    }
}
