package com.gaurav.nasa_pod.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gaurav.nasa_pod.api.ApiResponse
import com.gaurav.nasa_pod.data.model.Apod
import com.gaurav.nasa_pod.data.repo.ApodRepository
import com.gaurav.nasa_pod.data.repo.DbApodRepository
import com.gaurav.nasa_pod.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(
    private val apodRepository: ApodRepository,
    private val dbApodRepository: DbApodRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    lateinit var content: LiveData<ApiResponse<MutableList<Apod>>>

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun apods(): Flow<PagingData<Apod>> {
        return flowOf(
            clearListCh.receiveAsFlow().map { PagingData.empty() },
            dbApodRepository.fetchApodPage().cachedIn(viewModelScope)
        ).flattenMerge(2)
    }

    fun updateApod(apod: Apod) = viewModelScope.launch {
        apodRepository.updateApod(apod)
    }

}