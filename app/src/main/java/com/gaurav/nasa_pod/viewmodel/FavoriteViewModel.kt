package com.gaurav.nasa_pod.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.nasa_pod.data.model.Apod
import com.gaurav.nasa_pod.data.repo.ApodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val apodRepository: ApodRepository) :
    ViewModel() {
    val favorites = apodRepository.getFavorite()

    fun updateApod(apod: Apod) = viewModelScope.launch {
        apodRepository.updateApod(apod)
    }

}