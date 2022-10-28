package com.ianpedraza.dogedex.ui.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {
    private val _isCameraReady = MutableLiveData<Boolean>()
    val isCameraReady: LiveData<Boolean> get() = _isCameraReady

    private val _takenPhoto = MutableLiveData<Uri?>()
    val takenPhoto: LiveData<Uri?> get() = _takenPhoto

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
}
