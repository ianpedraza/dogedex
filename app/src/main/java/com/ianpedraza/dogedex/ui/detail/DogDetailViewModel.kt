package com.ianpedraza.dogedex.ui.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianpedraza.dogedex.usecases.AddDogToUserUseCase
import com.ianpedraza.dogedex.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel
@Inject
constructor(
    private val addDogToUserUseCase: AddDogToUserUseCase
) : ViewModel() {

    private val _addDogStatus = mutableStateOf<DataState<Boolean>?>(null)
    val addDogStatus: State<DataState<Boolean>?> get() = _addDogStatus

    fun resetAddSogStatus() {
        _addDogStatus.value = null
    }

    fun addDog(dogId: Long) {
        viewModelScope.launch {
            addDogToUserUseCase(dogId).onEach { dataState ->
                _addDogStatus.value = dataState
            }.launchIn(viewModelScope)
        }
    }
}
