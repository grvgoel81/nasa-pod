package com.gaurav.nasa_pod.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.gaurav.nasa_pod.AppExecutors
import com.gaurav.nasa_pod.api.ApiResponse
import com.gaurav.nasa_pod.api.ApodApi
import com.gaurav.nasa_pod.data.db.AppDatabase
import com.gaurav.nasa_pod.data.db.dao.ApodDao
import com.gaurav.nasa_pod.data.model.Apod
import com.gaurav.nasa_pod.di.NetworkModule
import com.gaurav.nasa_pod.util.DateUtil
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApodRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    @NetworkModule.ApiWithLiveDataAdapter private val apodApi: ApodApi,
    private val db: AppDatabase,
    private val apodDao: ApodDao
) {
    suspend fun updateApod(apod: Apod) {
        apodDao.update(apod)
    }

    fun getTodayContent(): LiveData<Resource<Apod>> {
        return object : NetworkBoundResource<Apod, Apod>(appExecutors) {
            override fun saveCallResult(item: Apod) {
                apodDao.insert(item)
            }

            override fun shouldFetch(data: Apod?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<Apod> {
                return apodDao.getByDateLiveData(DateUtil.todayDate())
            }

            override fun createCall(): LiveData<ApiResponse<Apod>> {
                return apodApi.getTodayContentLive()
            }
        }.asLiveData()
    }


    fun getApods(endDate: String, count: Int): LiveData<Resource<MutableList<Apod>>> {
        return object : NetworkBoundResource<MutableList<Apod>, Array<Apod>>(appExecutors) {
            override fun saveCallResult(item: Array<Apod>) {
                apodDao.insertArray(*item)
            }

            override fun shouldFetch(data: MutableList<Apod>?): Boolean {
                return data == null || data.size < count || data[0].date != DateUtil.todayDate()
            }

            override fun loadFromDb(): LiveData<MutableList<Apod>> {
                return apodDao.getByDateCount(endDate, count)
            }

            override fun createCall(): LiveData<ApiResponse<Array<Apod>>> {
                val startDate = DateUtil.getDateBeforeDate(DateUtil.todayDate(), count)
                return apodApi.getContents(startDate = startDate)
            }
        }.asLiveData()
    }

    fun getFavorite(): LiveData<MutableList<Apod>> {
        return apodDao.getFavorite()
    }

    fun getApodLiveData(apodId: String): LiveData<Apod> {
        return apodDao.getApodFlow(apodId.toInt()).asLiveData()
    }

    suspend fun getApod(apodId: String): Apod {
        return apodDao.getApod(apodId.toInt())
    }

    fun isFavorite(apodId: String): Flow<Boolean> {
        return apodDao.isFavorite(apodId.toInt())
    }
}