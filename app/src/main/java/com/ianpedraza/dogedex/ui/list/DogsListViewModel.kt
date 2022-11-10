package com.ianpedraza.dogedex.ui.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianpedraza.dogedex.domain.models.Dog
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
    private val getDogsCollectionUseCase: GetDogsCollectionUseCase
) : ViewModel() {

    private val _dogsList = mutableStateOf<DataState<List<Dog>>?>(null)
    val dogsList: State<DataState<List<Dog>>?> get() = _dogsList

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

    fun reset() {
        _dogsList.value = null
    }
}
