package com.bewe.suitmediamobiledev

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bewe.suitmediamobiledev.data.repository.DataRepository
import com.bewe.suitmediamobiledev.ui.screens.third.ThirdScreenViewModel

class ViewModelFactory(private val repository: DataRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdScreenViewModel::class.java)) {
            return ThirdScreenViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}