package com.ian.payback.image.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ian.payback.image.data.source.local.dao.ImageInfoDao

import com.ian.payback.image.domain.model.ImageInfo

@Database(entities = [ImageInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val imageInfoDao: ImageInfoDao


}
