package com.ian.payback.image.data.source.remote


import com.ian.payback.image.domain.model.ImageInfo
import com.ian.payback.image.util.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {


    @GET("/api?key=" + Constants.PIXABY_API_KEY)
    fun getImages(
        @Query("q") q: String
    ): Single<List<ImageInfo>>
}