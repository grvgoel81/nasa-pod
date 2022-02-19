package com.gaurav.nasa_pod.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gaurav.nasa_pod.data.model.Apod
import com.gaurav.nasa_pod.data.repo.ApodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApodDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apodRepository: ApodRepository,
) : ViewModel() {

    private val apodId = savedStateHandle.get<String>(APOD_ID_SAVED_STATE_KEY)!!
    val apod = apodRepository.getApodLiveData(apodId)

    fun addApodToFavorite(apod: Apod) = viewModelScope.launch {
        apodRepository.updateApod(apod)
    }

    val isFavorite = apodRepository.isFavorite(apodId).asLiveData()
    suspend fun getApods(): Apod {
        return apodRepository.getApod(apodId)
    }
    companion object {
        const val APOD_ID_SAVED_STATE_KEY = "apodId"
    }
}