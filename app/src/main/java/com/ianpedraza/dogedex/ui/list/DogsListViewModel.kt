package com.ianpedraza.dogedex.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.usecases.AddDogToUserUseCase
import com.ianpedraza.dogedex.usecases.GetDogsCollectionUseCase
import com.ianpedraza.dogedex.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel
@Inject constructor(
    private val getDogsCollectionUseCase: GetDogsCollectionUseCase,
    private val addDogToUserUseCase: AddDogToUserUseCase
) : ViewModel() {

    private val _dogsList = MutableLiveData<DataState<List<Dog>>>()
    val dogsList: LiveData<DataState<List<Dog>>> get() = _dogsList

    private val _addDogStatus = MutableLiveData<DataState<Boolean>>()
    val addDogStatus: LiveData<DataState<Boolean>> = _addDogStatus

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            getDogsCollectionUseCase().onEach { dataState ->
                _dogsList.value = dataState
            }.launchIn(viewModelScope)
        }
    }

    fun addDog(dogId: Long) {
        viewModelScope.launch {
            addDogToUserUseCase(dogId).onEach { dataState ->
                _addDogStatus.value = dataState
            }.launchIn(viewModelScope)
        }
    }
}
