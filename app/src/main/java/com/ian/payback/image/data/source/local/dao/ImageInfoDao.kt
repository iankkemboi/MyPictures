package com.ian.payback.image.data.source.local.dao


import androidx.room.*
import com.ian.payback.image.domain.model.ImageInfo

@Dao
interface ImageInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(images: List<ImageInfo>): List<Long>

    @Query("SELECT * FROM ImageInfo")
    fun loadAll(): List<ImageInfo>

    @Delete
    fun delete(ImageInfo: ImageInfo)

    @Query("DELETE FROM ImageInfo")
    fun deleteAll()

    @Query("SELECT * FROM ImageInfo where id = :imageId")
    fun loadOneById(imageId: Long): ImageInfo?


    @Update
    fun update(ImageInfo: ImageInfo)

}