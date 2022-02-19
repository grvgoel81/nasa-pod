package com.gaurav.nasa_pod.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gaurav.nasa_pod.data.db.dao.ApodDao
import com.gaurav.nasa_pod.data.model.Apod

@Database(
    entities = [Apod::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apodDao(): ApodDao
}