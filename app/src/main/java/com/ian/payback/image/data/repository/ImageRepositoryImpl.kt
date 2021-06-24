package com.ian.payback.image.data.repository

import com.ian.payback.image.data.source.local.AppDatabase
import com.ian.payback.image.data.source.remote.RetrofitService
import com.ian.payback.image.domain.model.ImageInfo
import com.ian.payback.image.domain.repository.ImageRepository
import com.ian.payback.image.domain.repository.NetworkBoundResource
import io.reactivex.Single

class ImageRepositoryImpl(
    private val database: AppDatabase,
    private val retrofitService: RetrofitService
) : ImageRepository {


    private val currentUserResource = object : NetworkBoundResource<List<ImageInfo>>() {
        override fun saveCallResult(item: List<ImageInfo>) {
            database.imageInfoDao.insertAll(item)
        }

        override fun loadFromDb(): Single<List<ImageInfo>> {
            return Single.just(database.imageInfoDao.loadAll())
        }

        override fun createCall(searchString: String): Single<List<ImageInfo>> {

            return retrofitService.getImages(searchString)
        }

        override fun processResult(it: List<ImageInfo>): List<ImageInfo> {
            return it
        }

    }


    override fun getImages(searchString: String): Single<List<ImageInfo>> {
        return currentUserResource.asSingle(searchString)
    }
}

