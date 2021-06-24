package com.ian.payback.image.domain.repository


import com.ian.payback.image.domain.model.ImageInfo
import io.reactivex.Single

interface ImageRepository {

    fun getImages(searchString: String): Single<List<ImageInfo>>


}