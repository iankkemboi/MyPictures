package com.ian.payback.image.data.db

import android.content.Context
import androidx.room.Room

import androidx.test.runner.AndroidJUnit4

import com.ian.payback.image.data.source.local.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import androidx.test.core.app.ApplicationProvider
import com.ian.payback.image.util.TestUtil
import junit.framework.Assert.*
import org.junit.Test


import java.io.IOException


@RunWith(AndroidJUnit4::class)
class ImageDaoTest {

    private lateinit var mDatabase: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun isImageListEmpty(){
        assertEquals(0,mDatabase.imageInfoDao.loadAll().size)
    }


    @Test
    @Throws(Exception::class)
    fun insertImages() {
        val imageList = TestUtil.makeImageInfoList(10)
        val insertedImages = mDatabase.imageInfoDao.insertAll(imageList)
        assertNotNull(insertedImages)
    }



    @Test
    @Throws(Exception::class)
    fun retrieveImages(){
        val imageList = TestUtil.makeImageInfoList(10)
        val insertedImages = mDatabase.imageInfoDao.insertAll(imageList)

        val loadedPhotos = mDatabase.imageInfoDao.loadAll()
        assertEquals(insertedImages,loadedPhotos)
    }


    @Test
    @Throws(Exception::class)
    fun deleteAllImages(){
        mDatabase.imageInfoDao.deleteAll()
        val loadedAllPhotos = mDatabase.imageInfoDao.loadAll()
        assert(loadedAllPhotos.isEmpty())
    }




}