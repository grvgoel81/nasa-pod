package com.gaurav.nasa_pod.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaurav.nasa_pod.api.ApodApi
import com.gaurav.nasa_pod.data.db.dao.ApodDao
import com.gaurav.nasa_pod.data.model.Apod
import com.gaurav.nasa_pod.di.NetworkModule
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbApodRepository @Inject constructor(
    @NetworkModule.ApiNormal private val apodApi: ApodApi,
    private val apodDao: ApodDao
) {
    companion object {
        const val DEFAULT_PAGE_SIZE = 8
    }

    @ExperimentalPagingApi
    @Inject
    lateinit var remoteMediator: ApodRemoteMediator

    @OptIn(ExperimentalPagingApi::class)
    fun fetchApodPage(): Flow<PagingData<Apod>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            remoteMediator = remoteMediator
        ) {
            apodDao.getAll()
        }.flow
    }

}
