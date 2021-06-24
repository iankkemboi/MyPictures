package com.ian.payback.image.domain.usecase

import com.ian.payback.image.domain.model.ImageInfo
import com.ian.payback.image.domain.repository.ImageRepository
import io.reactivex.Single
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(private val repository: ImageRepository) {


    fun getImages(searchStr: String): Single<List<ImageInfo>> {

        return repository.getImages(searchStr)
    }
}