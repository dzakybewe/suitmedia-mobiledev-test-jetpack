package com.bewe.suitmediamobiledev.ui.screens.third

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bewe.suitmediamobiledev.common.UiState
import com.bewe.suitmediamobiledev.data.remote.model.DataItem
import com.bewe.suitmediamobiledev.data.remote.model.UsersResponse
import com.bewe.suitmediamobiledev.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThirdScreenViewModel(private val repository: DataRepository) : ViewModel() {
    private val _result = MutableStateFlow<UiState<UsersResponse>>(UiState.Loading)
    val result: StateFlow<UiState<UsersResponse>> get() = _result

    private var currentPage = 1

    private val _list = MutableStateFlow<List<DataItem>>(emptyList())
    val list: StateFlow<List<DataItem>> get() = _list

    private val _isMoreDataAvailable = MutableStateFlow(true)
    val isMoreDataAvailable: StateFlow<Boolean> get() = _isMoreDataAvailable

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllUsers()
        }
    }

    private suspend fun getAllUsers(page: Int = 1) {
        viewModelScope.launch {
            try {
                val response = repository.getAllUsers(page)
                _result.value = response
                if (response is UiState.Success) {
                    currentPage = page
                    _list.value += response.data.data
                    if (response.data.data.isEmpty()) {
                        _isMoreDataAvailable.value = false
                    }
                }
            } catch (e: Exception) {
                _result.value = UiState.Error("Error on ViewModel: ${e.message}")
            }
        }
    }

    suspend fun loadNextPage() {
        val nextPage = currentPage + 1
        if (_isMoreDataAvailable.value) {
            getAllUsers(nextPage)
        }
    }
}