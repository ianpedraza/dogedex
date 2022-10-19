package com.ianpedraza.dogedex.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.usecases.GetAllDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel
@Inject
constructor(
    private val getAllDogsUseCase: GetAllDogsUseCase
) : ViewModel() {

    private val _dogsList = MutableLiveData<List<Dog>>()
    val dogsList: LiveData<List<Dog>> get() = _dogsList

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _dogsList.value = getAllDogsUseCase()!!
        }
    }
}
